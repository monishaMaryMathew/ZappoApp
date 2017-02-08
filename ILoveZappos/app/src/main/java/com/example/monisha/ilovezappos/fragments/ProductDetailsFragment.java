package com.example.monisha.ilovezappos.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.monisha.ilovezappos.R;
import com.example.monisha.ilovezappos.activity.ProductPageActivity;
import com.example.monisha.ilovezappos.databinding.FragmentProductDetailsBinding;
import com.example.monisha.ilovezappos.models.ProductModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnProductDetailsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnProductDetailsFragmentInteractionListener mListener;
    private ProductModel productModel = new ProductModel();

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductDetailsFragment newInstance(String param1, String param2) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_product_details, container, false);

        FragmentProductDetailsBinding binding = FragmentProductDetailsBinding.inflate(inflater, container, false);
        productModel = ((ProductPageActivity) getActivity()).currSearchResultModel.getFirstItem();
        binding.setProduct(productModel);
        //TODO add other params OR use a constructor
        rootView = binding.getRoot();
        Bitmap bmp = productModel.getImageBitMap();
        ((ImageView) rootView.findViewById(R.id.product_image_view)).setImageBitmap(bmp);
        binding.setHandler(new Handler());
        ((ProductPageActivity) getActivity()).setFABVisible(true);
        return rootView;
    }

    public class Handler {
        public void intentToBrowser() {
            String url = productModel.getProductUrl();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
            browserIntent.setData(Uri.parse(url));
            startActivity(browserIntent);
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onProductDetailsFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProductDetailsFragmentInteractionListener) {
            mListener = (OnProductDetailsFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnProductDetailsFragmentInteractionListener {
        // TODO: Update argument type and name
        void onProductDetailsFragmentInteraction(Uri uri);
    }

}
