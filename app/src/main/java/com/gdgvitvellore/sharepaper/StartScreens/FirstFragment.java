package com.gdgvitvellore.sharepaper.StartScreens;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdgvitvellore.sharepaper.R;
import com.gdgvitvellore.sharepaper.Utils;

/**
 * Created by ramkishorevs on 15/02/17.
 */

public class FirstFragment extends Fragment {

    TextView tv1, tv2;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.start_first_fragment, container, false);
        init(v);
        setfonts();
        return v;
    }

    private void init(View view) {
        tv1 = (TextView) view.findViewById(R.id.tv1);
        tv2 = (TextView) view.findViewById(R.id.tv2);
    }

    private void setfonts()
    {
        tv2.setTypeface(new Utils().getFontType(Utils.HEADING_FONT,context));
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context=activity;

    }
}