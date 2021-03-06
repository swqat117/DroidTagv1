package com.quascenta.petersroad.droidtag.Renderer;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pedrogomez.renderers.Renderer;
import com.quascenta.petersroad.droidtag.R;
import com.quascenta.petersroad.droidtag.SensorCollection.model.DeviceViewModel;
import com.quascenta.petersroad.droidtag.widgets.Line_view;
import com.quascenta.petersroad.droidtag.widgets.TextDrawable;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AKSHAY on 1/30/2017.
 */

public class DeviceRenderer extends Renderer<DeviceViewModel> {


    private final Context context;
    View mview;
    @Bind(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @Bind(R.id.iv_icon_alert)
    ImageView iv_status;
    @Bind(R.id.source_company_name)
    TextView mSource_company_name;
    @Bind(R.id.source_location)
    TextView mSource_location;
    @Bind(R.id.destination_company_name)
    TextView mDestination_company_name;
    @Bind(R.id.destination_location)
    TextView mDestination_location;
    @Bind(R.id.customer_tracking_id)
    TextView customer_tracking_id;
    @Bind(R.id.transaction_id)
    TextView transaction_id;
    @Bind(R.id.device_name)
    TextView device;
    Line_view line_view;


    public DeviceRenderer(Context context) {

        this.context = context;

    }

    /**
     * Apply ButterKnife inject method to support view injections.
     */
    @Override
    protected void setUpView(View view) {
        mview = view;
        line_view = (Line_view) view.findViewById(R.id.line4);
        ButterKnife.bind(this, view);

    }

    @Override
    protected void hookListeners(View view) {
        //Empty

    }

    /**
     * Inflate the layout associated to this renderer
     */
    @Override
    protected View inflate(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return layoutInflater.inflate(R.layout.device_sample_show_row, viewGroup, false);
    }

    /**
     * Render the DeviceViewModel information.
     */
    @Override
    public void render() {
        DeviceViewModel deviceViewModel = getContent();
        mSource_company_name.setText(deviceViewModel.getStart_company());
        mDestination_company_name.setText(deviceViewModel.getDestination_company());
        mSource_location.setText(deviceViewModel.getStart_from());
        mDestination_location.setText(deviceViewModel.getDestination_to());
        customer_tracking_id.setText("#1021110" + String.valueOf(deviceViewModel.getDEVICE_ID()));
        line_view.setState(deviceViewModel.getStatus());
        transaction_id.setText("Trans ID: 121454752");
        device.setText(deviceViewModel.getTitle());
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .withBorder(4) /* thickness in px */
                .endConfig()
                .buildRoundRect((deviceViewModel.getTitle().substring(0, 1)), Color.RED, 10);
        iv_status.setImageDrawable(drawable);


    }




    public interface OnItemClickListener {
        void onItemClick(DeviceViewModel item);
    }



}
