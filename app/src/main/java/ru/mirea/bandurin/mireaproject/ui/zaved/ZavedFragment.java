package ru.mirea.bandurin.mireaproject.ui.zaved;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import ru.mirea.bandurin.mireaproject.databinding.FragmentZavedBinding;

public class ZavedFragment extends Fragment {

    private FragmentZavedBinding binding;
    private MapView mapView = null;
    private boolean isWork = true;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ZavedViewModel slideshowViewModel =
                new ViewModelProvider(this).get(ZavedViewModel.class);

        binding = FragmentZavedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mapView=binding.mapView;
        mapView.setZoomRounding(true);
        mapView.setMultiTouchControls(true);
        int backgroundPermissionStatus = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        int finePermissionStatus = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION);
        int coarsePermissionStatus = ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION);

        if (coarsePermissionStatus == PackageManager.PERMISSION_GRANTED && finePermissionStatus
                == PackageManager.PERMISSION_GRANTED) {
            isWork = true;
        } else {
// Выполняется запрос к пользователь на получение необходимых разрешений
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        if(isWork){
            MyLocationNewOverlay locationNewOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getActivity().getApplicationContext()), mapView);
            locationNewOverlay.enableMyLocation();
            mapView.getOverlays().add(locationNewOverlay);

            locationNewOverlay.runOnFirstFix(new Runnable() {
                @Override
                public void run() {
                    try{
                        double latitude = locationNewOverlay.getMyLocation().getLatitude();
                        double longitude = locationNewOverlay.getMyLocation().getLongitude();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                IMapController mapController = mapView.getController();
                                mapController.setZoom(12.0);
                                GeoPoint point = new GeoPoint(55.74, 37.70);
                                mapController.setCenter(point);
                            }
                        });
                    }
                    catch(Exception e){}
                }
            });
        }
        CompassOverlay compassOverlay = new CompassOverlay(getActivity().getApplicationContext(), new InternalCompassOrientationProvider(getActivity().getApplicationContext()), mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);

        final Context context = getContext().getApplicationContext();
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        ScaleBarOverlay scaleBarOverlay = new ScaleBarOverlay(mapView);
        scaleBarOverlay.setCentred(true);
        scaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        mapView.getOverlays().add(scaleBarOverlay);

        Marker marker = new Marker(mapView);
        marker.setPosition(new GeoPoint(55.98987843651076, 37.21521116544813));
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Toast.makeText(getActivity().getApplicationContext(),"Москва, Зеленоградский административный округ, парк 40-летия Победы",
                        Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity().getApplicationContext(),"Парк победы",
                        Toast.LENGTH_SHORT).show();

                return true;
            }
        });
        mapView.getOverlays().add(marker);

        marker.setIcon(ResourcesCompat.getDrawable(getResources(), org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null));

        marker.setTitle("Парк победы");


        Marker marker2 = new Marker(mapView);
        marker2.setPosition(new GeoPoint(55.79387882650709, 37.673013703560386));
        marker2.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Toast.makeText(getActivity().getApplicationContext(),"\n" +
                                "ул. Сокольнический Вал, 1, стр. 1",
                        Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity().getApplicationContext(),"Парк сокольники",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mapView.getOverlays().add(marker2);

        marker2.setIcon(ResourcesCompat.getDrawable(getResources(), org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null));

        marker2.setTitle("Парк сокольники");


        Marker marker3 = new Marker(mapView);
        marker3.setPosition(new GeoPoint(55.79629994160211, 37.71382883386971));
        marker3.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Toast.makeText(getActivity().getApplicationContext(),"\n" +
                                "Москва, Сокольническая линия, метро Преображенская площадь",
                        Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity().getApplicationContext(),"Преображенка",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mapView.getOverlays().add(marker3);

        marker3.setIcon(ResourcesCompat.getDrawable(getResources(), org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null));

        marker3.setTitle("Преображенка");
        


       // final TextView textView = binding.textZaved;
        //slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
    @Override
    public void onResume() {
        super.onResume();
        Configuration.getInstance().load(getActivity().getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()));
        if (mapView != null) {
            mapView.onResume();
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        Configuration.getInstance().save(getActivity().getApplicationContext(),

                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()));

        if (mapView != null) {
            mapView.onPause();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}