package cn.flow.ryvonne.flowchartapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import cn.flow.ryvonne.flowchartapp.R;
import cn.flow.ryvonne.flowchartapp.view.FlowItem;

public class SingleFragment extends Fragment {
    public SingleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FlowItem item1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single, container, false);
//        item1 = (FlowItem) view.findViewById(R.id.fl_1);
//        item1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(),"toast",Toast.LENGTH_SHORT).show();
//            }
//        });
        return view;
    }

}
