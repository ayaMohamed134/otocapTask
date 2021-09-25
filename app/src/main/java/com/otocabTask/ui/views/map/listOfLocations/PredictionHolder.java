package com.otocabTask.ui.views.map.listOfLocations;

import static com.otocabTask.utils.LogUtils.LOGE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.otocabTask.databinding.SourceItemBinding;

public class PredictionHolder extends RecyclerView.ViewHolder {

    private final SourceItemBinding binding;
    private OnClickListItemListener onClickListItemListener;

    public PredictionHolder(@NonNull SourceItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(@NonNull AutocompletePrediction prediction) {
        LOGE("source: " + "name: " + prediction.getPrimaryText(null).toString());
        setName(prediction.getPrimaryText(null).toString());
        binding.tvName.setOnClickListener(v -> onClickListItemListener.onClickItem(prediction));
    }

    private void setName(@Nullable String name) {
        binding.tvName.setText(name);
    }

    public void setListener(OnClickListItemListener onClickListItemListener) {
        this.onClickListItemListener = onClickListItemListener;
    }
}
