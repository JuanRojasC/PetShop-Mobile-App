package com.petshop.ui.maps;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.petshop.R;
import com.petshop.databinding.FragmentMapboxBinding;

import java.util.ArrayList;
import java.util.List;


public class MapBoxFragment extends Fragment implements OnMapReadyCallback {

    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";
    private FragmentMapboxBinding binding;
    private Double latitude;
    private Double longitude;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Mapbox.getInstance(requireContext(), getResources().getString(R.string.mapbox_access_token));
        binding = FragmentMapboxBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);

        toolbar.post(() -> {
            Drawable d = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_blue, null);
            toolbar.setNavigationIcon(d);
        });

        MapView mapView = root.findViewById(R.id.mapboxFullScreen);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        latitude = requireArguments().getDouble("latitude");
        longitude = requireArguments().getDouble("longitude");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {

        mapboxMap.setCameraPosition(new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(12.0).build());

        List<Feature> symbolLayerIconFeatureList = new ArrayList<>();
        symbolLayerIconFeatureList.add(Feature.fromGeometry(Point.fromLngLat(longitude, latitude)));

        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/mapbox/cjf4m44iw0uza2spb3q0a7s41")

            // Add the SymbolLayer icon image to the map style
            .withImage(ICON_ID, BitmapFactory.decodeResource(getResources(), R.drawable.map_maker))

            // Adding a GeoJson source for the SymbolLayer icons.
            .withSource(new GeoJsonSource(SOURCE_ID, FeatureCollection.fromFeatures(symbolLayerIconFeatureList)))

            // Adding the actual SymbolLayer to the map style. An offset is added that the bottom of the red
            // marker icon gets fixed to the coordinate, rather than the middle of the icon being fixed to
            // the coordinate point. This is offset is not always needed and is dependent on the image
            // that you use for the SymbolLayer icon.
            .withLayer(new SymbolLayer(LAYER_ID, SOURCE_ID)
                    .withProperties(
                            iconImage(ICON_ID),
                            iconAllowOverlap(true),
                            iconIgnorePlacement(true)
                    )
            ), style -> { });

    }
}