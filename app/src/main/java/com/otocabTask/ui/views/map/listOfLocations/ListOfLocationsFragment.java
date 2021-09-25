package com.otocabTask.ui.views.map.listOfLocations;

import static com.otocabTask.utils.LogUtils.LOGD;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.otocabTask.BR;
import com.otocabTask.R;
import com.otocabTask.constants.ViewModelFactory;
import com.otocabTask.data.model.Location;
import com.otocabTask.databinding.FragmentListOfLocationsBinding;
import com.otocabTask.databinding.SourceItemBinding;
import com.otocabTask.ui.base.BaseFragment;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Aya Mohamed 12/11/2019
 */
public class ListOfLocationsFragment extends BaseFragment<FragmentListOfLocationsBinding, ListOfLocationsViewModel> implements OnClickListItemListener {

    public static final String TAG = ListOfLocationsFragment.class.getSimpleName();
    @Inject
    ViewModelFactory factory;
    private ListOfLocationsViewModel listOfLocationsViewModel;
    private FragmentListOfLocationsBinding mBinding;

    private static final CollectionReference sourceCollection =
            FirebaseFirestore.getInstance().collection("Source");

    private boolean isFromSource = false;
    private String placeName = "";
    private PlacesClient placesClient = null;
    private Location sourceLocation = null;
    private Location destinationLocation = null;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_list_of_locations;
    }

    @Override
    public ListOfLocationsViewModel getViewModel() {
        listOfLocationsViewModel = new ViewModelProvider(this, factory).get(ListOfLocationsViewModel.class);
        return listOfLocationsViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding = getViewDataBinding();
        setupUI();
        subscribeToLiveData();
    }

    private void subscribeToLiveData() {

    }

    private void setupUI() {
        ListOfLocationsFragmentArgs args = ListOfLocationsFragmentArgs.fromBundle(requireArguments());
        if (args != null) {
            isFromSource = args.getIsFromSource();
        }
        setupListUI();
        setupPlacesApi();
        mBinding.etYourLocation.setOnClickListener(v -> {
            attachSourceRecyclerViewAdapter();
            mBinding.etYourLocation.setText("");
        });
        mBinding.etYourDestination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fetchPredictions(s.toString());
            }
        });
        mBinding.btnBack.setOnClickListener(v -> {
            ListOfLocationsFragmentDirections.ActionListOfLocationsFragmentToMapFragment action =
                    ListOfLocationsFragmentDirections.actionListOfLocationsFragmentToMapFragment();
            action.setDestinationLocation(destinationLocation);
            action.setSourceLocation(sourceLocation);
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(action);
        });
    }

    private void setupPlacesApi() {
        // Initialize Places.
        Places.initialize(getActivity(), "AIzaSyCt9ApcngmV7Zj_XR8h5hoznS1EaYuPLhI");
        // Create a new Places client instance.
        placesClient = Places.createClient(getActivity());

    }

    private void setupListUI() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);

        mBinding.rvLocations.setHasFixedSize(true);
        mBinding.rvLocations.setLayoutManager(manager);

        mBinding.rvLocations.addOnLayoutChangeListener((view, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (bottom < oldBottom) {
                mBinding.rvLocations.postDelayed(() -> mBinding.rvLocations.smoothScrollToPosition(
                        0), 100);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isFromSource) {
            fetchPredictions(placeName);
        } else {
            attachSourceRecyclerViewAdapter();
        }
    }

    private void fetchPredictions(String placeName) {
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setQuery(placeName)
                .setCountry("eg")
                .build();
        // Add a listener to handle the response.
        placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
            List<AutocompletePrediction> predictions = response.getAutocompletePredictions();
            attachPredictionsRecyclerViewAdapter(predictions);
        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                Log.e(TAG, "Place not found: " + apiException.getStatusCode());
            }
        });
    }

    private void attachPredictionsRecyclerViewAdapter(List<AutocompletePrediction> predictions) {
        final RecyclerView.Adapter adapter = newPredictionsAdapter(predictions);
        mBinding.rvLocations.setAdapter(adapter);
    }

    private RecyclerView.Adapter newPredictionsAdapter(List<AutocompletePrediction> predictions) {
        return new RecyclerView.Adapter<PredictionHolder>() {
            @NonNull
            @Override
            public PredictionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                SourceItemBinding sourceItemBinding = SourceItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                PredictionHolder predictionHolder = new PredictionHolder(sourceItemBinding);
                predictionHolder.setListener(ListOfLocationsFragment.this::onClickItem);
                return predictionHolder;
            }

            @Override
            public void onBindViewHolder(@NonNull PredictionHolder holder, int position) {
                holder.bind(predictions.get(position));
            }

            @Override
            public int getItemCount() {
                mBinding.tvEmpty.setVisibility(predictions.size() == 0 ? View.VISIBLE : View.GONE);
                return predictions.size();
            }
        };
    }

    private void attachSourceRecyclerViewAdapter() {
        final RecyclerView.Adapter adapter = newSourceAdapter();
        mBinding.rvLocations.setAdapter(adapter);
    }

    @NonNull
    private RecyclerView.Adapter newSourceAdapter() {
        FirestoreRecyclerOptions<Location> options =
                new FirestoreRecyclerOptions.Builder<Location>()
                        .setQuery(sourceCollection, Location.class)
                        .setLifecycleOwner(this)
                        .build();

        return new FirestoreRecyclerAdapter<Location, SourceHolder>(options) {

            @NonNull
            @Override
            public SourceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                SourceItemBinding sourceItemBinding = SourceItemBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                SourceHolder sourceHolder = new SourceHolder(sourceItemBinding);
                sourceHolder.setListener(ListOfLocationsFragment.this::onClickItem);
                return sourceHolder;
            }

            @Override
            protected void onBindViewHolder(@NonNull SourceHolder holder, int position, @NonNull Location model) {
                LOGD("source: " + "name: " + model.getName() + "lat: " + model.getLatitude() + "long: " + model.getLongitude());
                holder.bind(model);
            }

            @Override
            public void onDataChanged() {
                mBinding.tvEmpty.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
            }
        };
    }

    @Override
    public void onClickItem(Object item) {
        hideKeyboard();
        if (item instanceof AutocompletePrediction) {
            mBinding.etYourDestination.setText(((AutocompletePrediction) item).getPrimaryText(null).toString());
            findLocationData((AutocompletePrediction) item);
        } else if (item instanceof Location) {
            mBinding.etYourLocation.setText(((Location) item).getName());
            saveSourceLocation((Location) item);
        }
    }

    private void findLocationData(AutocompletePrediction prediction) {
        // Define a Place ID.
        String placeId = prediction.getPlaceId();
        // Specify the fields to return.
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        // Construct a request object, passing the place ID and fields array.
        FetchPlaceRequest request = FetchPlaceRequest.builder(placeId, placeFields)
                .build();
        // Add a listener to handle the response.
        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
            Place place = response.getPlace();
            Log.i(TAG, "Place found: " + place.getName());
            Location location = new Location();
            location.setName(place.getName());
            location.setLatitude(place.getLatLng().latitude);
            location.setLongitude(place.getLatLng().longitude);
            saveDestinationSource(location);
        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                int statusCode = apiException.getStatusCode();
                // Handle error with given status code.
                Log.e(TAG, "Place not found: " + exception.getMessage());
            }
        });

    }

    private void saveSourceLocation(Location location) {
        this.sourceLocation = location;
    }

    private void saveDestinationSource(Location location) {
        this.destinationLocation = location;
    }
}