package com.otocabTask.ui.views.map;

import static com.otocabTask.utils.LogUtils.LOGD;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.otocabTask.BR;
import com.otocabTask.R;
import com.otocabTask.constants.ViewModelFactory;
import com.otocabTask.data.model.Location;
import com.otocabTask.databinding.FragmentMapBinding;
import com.otocabTask.ui.base.BaseFragment;
import com.otocabTask.utils.locationService.GpsTracker;

import javax.inject.Inject;

/**
 * Aya Mohamed 12/11/2019
 */
public class MapFragment extends BaseFragment<FragmentMapBinding, MapViewModel> {

    public static final String TAG = MapFragment.class.getSimpleName();
    @Inject
    ViewModelFactory factory;
    private MapViewModel mapViewModel;
    private FragmentMapBinding mBinding;
    private GpsTracker gpsTracker;
    private SupportMapFragment mapFragment;
    private static final double EARTHRADIUS = 6366198;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_map;
    }

    @Override
    public MapViewModel getViewModel() {
        mapViewModel = new ViewModelProvider(this, factory).get(MapViewModel.class);
        return mapViewModel;
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
    }

    private void setupUI() {
        setupMapView();
        MapFragmentArgs args = MapFragmentArgs.fromBundle(requireArguments());
        if (args != null){
            Location sourceLocation = args.getSourceLocation();
            if (sourceLocation != null){
                mBinding.etYourLocation.setText(sourceLocation.getName());
                mBinding.etYourLocation.clearFocus();
            }

            Location destinationLocation = args.getDestinationLocation();
            if (destinationLocation != null){
                mBinding.etYourDestination.setText(destinationLocation.getName());
                mBinding.etYourDestination.clearFocus();
            }

            if (mapFragment != null) {
                mapFragment.getMapAsync(googleMap -> {
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    if (sourceLocation != null){
                        Marker sourceMarker = googleMap.addMarker(new MarkerOptions().position(new LatLng(sourceLocation.getLatitude()
                                , sourceLocation.getLongitude())).
                                icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_location)));
                        builder.include(sourceMarker.getPosition());
                    }
                    if (destinationLocation != null){
                        Marker destinationMarker = googleMap.addMarker(new MarkerOptions().position(new LatLng(destinationLocation.getLatitude()
                                , destinationLocation.getLongitude())).
                                icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_destination_location)));
                        builder.include(destinationMarker.getPosition());
                    }
                    if (sourceLocation != null && destinationLocation != null) {
                        LatLngBounds tmpBounds = builder.build();
                        LatLng center = tmpBounds.getCenter();
                        LatLng northEast = move(center, 16000, 16000);
                        LatLng southWest = move(center, -16000, -16000);
                        builder.include(southWest);
                        builder.include(northEast);
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 10));
                        // Instantiates a new Polyline object and adds points to define a rectangle
                        PolylineOptions polylineOptions = new PolylineOptions()
                                .add(new LatLng(sourceLocation.getLatitude(), sourceLocation.getLongitude()))
                                .add(new LatLng(destinationLocation.getLatitude(), destinationLocation.getLongitude()))
                                .geodesic(true);
                        // Get back the mutable Polyline
                        googleMap.addPolyline(polylineOptions);
                    }
                });
            }

        }
        mBinding.btnHamburger.setOnClickListener(v -> goToListView(false));
        mBinding.etYourLocation.setOnClickListener(v -> goToListView(false));
        mBinding.etYourDestination.setOnClickListener(v -> goToListView(true));
    }

    private void goToListView(boolean isFromSource) {
        MapFragmentDirections.ActionMapFragmentToListOfLocationsFragment action
                = MapFragmentDirections.actionMapFragmentToListOfLocationsFragment();
        action.setIsFromSource(isFromSource);
        Navigation.findNavController(getActivity(), R.id.nav_host_fragment)
                .navigate(action);
    }

    private void setupMapView() {
        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        getLocation(mapFragment);
    }

    public void getLocation(SupportMapFragment mapFragment){
        gpsTracker = new GpsTracker(getActivity());
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            LOGD("location: " + "lat: " + latitude + "long: " + longitude);
            if (mapFragment != null) {
                mapFragment.getMapAsync(googleMap -> {
                    Marker marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).
                            icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_location)));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 12.0f));
                });
            }
        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    /**
     * Create a new LatLng which lies toNorth meters north and toEast meters
     * east of startLL
     */
    private static LatLng move(LatLng startLL, double toNorth, double toEast) {
        double lonDiff = meterToLongitude(toEast, startLL.latitude);
        double latDiff = meterToLatitude(toNorth);
        return new LatLng(startLL.latitude + latDiff, startLL.longitude
                + lonDiff);
    }

    private static double meterToLongitude(double meterToEast, double latitude) {
        double latArc = Math.toRadians(latitude);
        double radius = Math.cos(latArc) * EARTHRADIUS;
        double rad = meterToEast / radius;
        return Math.toDegrees(rad);
    }

    private static double meterToLatitude(double meterToNorth) {
        double rad = meterToNorth / EARTHRADIUS;
        return Math.toDegrees(rad);
    }

}