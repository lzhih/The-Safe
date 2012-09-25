package org.find.location;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.amap.mapapi.core.GeoPoint;
import com.amap.mapapi.map.MapActivity;
import com.amap.mapapi.map.MapController;
import com.amap.mapapi.map.MapView;
import com.amap.mapapi.map.MyLocationOverlay;
import org.find.location.R;

public class MyLocationOver extends MapActivity{
	
	private MapView mMapView;
	private MapController mMapController;
	//private GeoPoint point;
	private MyLocationOverlay mLocationOverlay;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapview);
		mMapView = (MapView) findViewById(R.id.mapView);
		mMapView.setBuiltInZoomControls(true);  
		mMapController = mMapView.getController();  
		//point = new GeoPoint((int) (39.90923 * 1E6),
				//(int) (116.397428 * 1E6));  
		//mMapController.setCenter(point);
		mMapController.setZoom(12);   
		mLocationOverlay = new MyLocationOverlay(this, mMapView);
		mMapView.getOverlays().add(mLocationOverlay);
		mLocationOverlay.runOnFirstFix(new Runnable() {
            public void run() {
            	handler.sendMessage(Message.obtain(handler, Constants.FIRST_LOCATION));
            }
        });
    }

    @Override
	protected void onPause() {
    	this.mLocationOverlay.disableMyLocation();
    	this.mLocationOverlay.disableCompass();
		super.onPause();
	}

	@Override
	protected void onResume() {
		this.mLocationOverlay.enableMyLocation();
		this.mLocationOverlay.enableCompass();
		super.onResume();		
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == Constants.FIRST_LOCATION) {
				mMapController.animateTo(mLocationOverlay.getMyLocation());
			}
		}
    };

}
