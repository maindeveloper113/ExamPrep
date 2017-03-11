package com.contractorsintelligence.application;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.contractorsintelligence.cis.R;
import com.contractorsintelligence.model.Movie;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;


public class CustomListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<Movie> movieItems;
	//ImageLoader imageLoader = Controller.getInstance().getImageLoader();
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;


	public CustomListAdapter(Activity activity, List<Movie> movieItems) {
		this.activity = activity;
		this.movieItems = movieItems;

	}

    public class ViewHolder {
        TextView SN;
        TextView NAME;
        ImageView Thumbnail;
        //TextView iOSURL;
        TextView androidURL;
    }

	@Override
	public int getCount() {
		return movieItems.size();
	}

	@Override
	public Object getItem(int location) {
		return movieItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

        final ViewHolder holder;

        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            holder = new ViewHolder();

            view = inflater.inflate(R.layout.list_row, null);

            holder.SN = (TextView) view.findViewById(R.id.SN);
            holder.NAME = (TextView) view.findViewById(R.id.tvName);
            //holder.iOSURL = (TextView) view.findViewById(R.id.iosURL);
            holder.androidURL = (TextView) view.findViewById(R.id.androidURL);
            holder.Thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

            view.setTag(holder);

        } else {

            holder = (ViewHolder) view.getTag();

        }

		options = new DisplayImageOptions.Builder()
				//.showImageOnLoading(R.drawable.ic_stub)
				//.showImageForEmptyUri(R.drawable.no_image)
				//.showImageOnFail(R.drawable.no_image)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(activity)
				.memoryCacheExtraOptions(480, 800) // default = device screen dimensions
				.diskCacheExtraOptions(480, 800, null)
				.threadPriority(Thread.NORM_PRIORITY - 2) // default
				.tasksProcessingOrder(QueueProcessingType.FIFO) // default
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024)
				.memoryCacheSizePercentage(13) // default
				.diskCacheSize(50 * 1024 * 1024)
				.diskCacheFileCount(100)
				.diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
				.imageDownloader(new BaseImageDownloader(activity)) // default
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
				.writeDebugLogs()
				.build();

		ImageLoader.getInstance().init(config);
/*
		if (inflater == null)
			inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null)
			convertView = inflater.inflate(R.layout.list_row, null);
        *
        */

		// getting movie data for the row
		Movie m = movieItems.get(position);
		// thumbnail image
        //imageLoader.getInstance().displayImage(m.getThumbnailUrl(), thumbNail, options);

        holder.SN.setText(m.getSN());
        holder.NAME.setText(m.getTitle());
        //holder.iOSURL.setText(m.getTitle());
        holder.androidURL.setText(m.getAndroidURL());

        final ProgressBar spinner = (ProgressBar) view.findViewById(R.id.loading);

        imageLoader.displayImage(m.getThumbnailUrl(), holder.Thumbnail, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                spinner.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                spinner.setVisibility(View.GONE);
                holder.Thumbnail.setVisibility(View.VISIBLE);
            }
        });

		// Listen for ListView Item Click
		view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

                String videoId = holder.androidURL.getText().toString().replace("https://youtu.be/","");
				//String videoId = holder.androidURL.getText().toString();
				Log.d("VideoIDs: ", "=> " + videoId);

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+videoId));
//				Intent intent = new Intent(Intent.ACTION_VIEW);
//				intent.setDataAndType(Uri.parse(videoId), "video/mp4");
                intent.putExtra("force_fullscreen",true);
                intent.putExtra("VIDEO_ID", videoId);
				intent.putExtra("count", (getCount()));
				activity.startActivity(intent);

//				VideoView videoView = (VideoView) findViewById(R.id.videoView);
////Use a media controller so that you can scroll the video contents
////and also to pause, start the video.
//				MediaController mediaController = new MediaController(this);
//				mediaController.setAnchorView(videoView);
//				videoView.setMediaController(mediaController);
//				videoView.setVideoURI(Uri.parse(videoUrl));
//				videoView.start();

			}
		});

        return view;
	}



}