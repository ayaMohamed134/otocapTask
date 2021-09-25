package com.otocabTask.ui.views.map.listOfLocations;

import static com.otocabTask.utils.LogUtils.LOGD;
import static com.otocabTask.utils.LogUtils.LOGE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.otocabTask.data.model.Location;
import com.otocabTask.databinding.SourceItemBinding;

public class SourceHolder  extends RecyclerView.ViewHolder {

    private final SourceItemBinding binding;
    private OnClickListItemListener onClickListItemListener;

    public SourceHolder(@NonNull SourceItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(@NonNull Location location) {
        LOGE("source: " + "name: " + location.getName() + "lat: " + location.getLatitude() + "long: " + location.getLongitude());
        setName(location.getName());
        binding.tvName.setOnClickListener(v -> onClickListItemListener.onClickItem(location));
    }

    private void setName(@Nullable String name) {
        binding.tvName.setText(name);
    }

    public void setListener(OnClickListItemListener onClickListItemListener) {
        this.onClickListItemListener = onClickListItemListener;
    }
}
