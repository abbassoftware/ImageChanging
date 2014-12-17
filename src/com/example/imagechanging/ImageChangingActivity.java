package com.example.imagechanging;

import java.io.InputStream;
import java.net.URL;

import android.support.v7.app.ActionBarActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

public class ImageChangingActivity extends ActionBarActivity {

	private Integer images [] = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3};
	
	private String imageUrls[] = {"http://abc.com/pic1.jpg",
								"http://abc.com/pic1.jpg",
								"http://abc.com/pic1.jpg"
								};
	
	private class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
		  ImageView bmImage;

		  public ImageDownloader(ImageView bmImage) {
		      this.bmImage = bmImage;
		  }

		  protected Bitmap doInBackground(String... urls) {
		      String url = urls[0];
		      Bitmap bitmap = null;
		      try {
		        InputStream in = new java.net.URL(url).openStream();
		        bitmap = BitmapFactory.decodeStream(in);
		      } catch (Exception e) {
		          Log.e("MyApp", e.getMessage());
		      }
		      return bitmap;
		  }

		  protected void onPostExecute(Bitmap result) {
		      bmImage.setImageBitmap(result);
		  }
		}
	private int currImage = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_changing);
		
		//initializeImageSwitcher();
		setInitialImage();
		setImageRotateListener();
		
	}

	private void initializeImageSwitcher() {
		final ImageSwitcher imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
		imageSwitcher.setFactory(new ViewFactory() {
			@Override
			public View makeView() {
				ImageView imageView = new ImageView(ImageChangingActivity.this);
				return imageView;
			}});
		
		imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
		imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
		
	}

	private void setImageRotateListener() {
		final Button rotatebutton = (Button) findViewById(R.id.btnRotateImage);
		rotatebutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				currImage++;
				if(currImage == 3) {
					currImage = 0;
				}
				setCurrentImage();
			}
		});
	}

	private void setInitialImage() {
		setCurrentImage();
	}
	
	private void setCurrentImage() {

		final ImageView imageView = (ImageView) findViewById(R.id.imageDisplay);
		imageView.setImageResource(images[currImage]);
		
		//final ImageSwitcher imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
		//imageSwitcher.setImageResource(images[currImage]);
		
		//ImageDownloader imageDownLoader = new ImageDownloader(imageView);
		//imageDownLoader.execute(imageUrls[currImage]);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_changing, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
