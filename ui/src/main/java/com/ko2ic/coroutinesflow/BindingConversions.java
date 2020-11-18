package com.ko2ic.coroutinesflow;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;
import androidx.databinding.BindingMethod;
import androidx.databinding.BindingMethods;

import com.google.android.material.navigation.NavigationView;
import com.ko2ic.coroutinesflow.common.ui.viewmodel.Action;

import org.jetbrains.annotations.Nullable;

@BindingMethods({
        @BindingMethod(
                type = NavigationView.class,
                attribute = "onNavigationItemSelected",
                method = "setNavigationItemSelectedListener"
        ),
})
public class BindingConversions {

    @BindingConversion
    public static View.OnClickListener toOnClickListener(final Action listener) {
        if (listener != null) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        listener.run();
                    } catch (Exception e) {
                        throw new IllegalArgumentException("例外が出ない想定", e);
                    }
                }
            };
        } else {
            return null;
        }
    }

    @BindingAdapter("android:visibility")
    public static void bindVisibility(@NonNull View view, @Nullable Boolean visible) {
        int visibility = (visible != null && visible) ? View.VISIBLE : View.GONE;
        view.setVisibility(visibility);
    }
}


