package ca.seneca.map524.smartsecretary.fragment;

import android.app.Fragment;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

import ca.seneca.map524.smartsecretary.R;

/**
 * Created by Wonho on 11/5/2016.
 */
public class GoogleMapFragment extends Fragment implements OnMapReadyCallback {
    private final static String TAG = "GoogleMapFragment";

    // Google Map
    private MapView mMapView;

    // Geocoder
    private Geocoder mGeocoder;

    private String mLocation;
    private TextView mTextTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        /*Bundle bundle = new Bundle();
        bundle = getArguments();
        mLocation = bundle.getString("location");*/

        // location
        if (mLocation != null) {
            mTextTitle = (TextView)rootView.findViewById(R.id.textTitle);
            mTextTitle.setText(mLocation.toUpperCase());
        }

        // initialize map
        MapsInitializer.initialize(this.getActivity());
        mMapView = (MapView)rootView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);

        return rootView;
    }

    /**
     * Google Map
     * @param googleMap
     */
    public void onMapReady(GoogleMap googleMap) {
        // locate
        LatLng latlng = searchLocation(mLocation);
        if (latlng == null) {
            latlng = new LatLng(43.6525, -79.381667);   // Toronto
            mTextTitle.setText("TORONTO");
        } else {
            mTextTitle.setText(mLocation.toUpperCase());
        }

        // move the location and draw marker
        googleMap.addMarker(new MarkerOptions().position(latlng).title(mLocation));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 12));
    }

    @Override
    public void onResume() {
        mMapView.onResume();
        super.onResume();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    /**
     * search location using Geocoder
     * @param location
     * @return
     */
    private LatLng searchLocation(String location) {
        List<Address> addressList = null;
        LatLng latlng = null;

        if (mGeocoder == null) {
            mGeocoder = new Geocoder(getActivity(), Locale.ENGLISH);
        }

        try {
            addressList = mGeocoder.getFromLocationName(location, 3);

            if (addressList != null) {
                latlng = new LatLng(addressList.get(0).getLatitude(), addressList.get(0).getLongitude());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return latlng;
    }

    /**
     * set location for Google map
     * @param location
     */
    public void setLocation(String location) {
        mLocation = location;
    }
}
