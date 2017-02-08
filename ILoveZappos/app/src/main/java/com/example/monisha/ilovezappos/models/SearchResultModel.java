package com.example.monisha.ilovezappos.models;

/**
 * Created by Monisha on 2/5/2017.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SearchResultModel {
    public String originalTerm = "";
    public int currentResultCount = 0;
    public int totalResultCount = 0;
    public String term = "";
    public ArrayList<ProductModel> results = null;
    public ProductModel firstItem = null;

    public ProductModel getFirstItem() {
        return firstItem;
    }

    public void setFirstItem(ProductModel firstItem) {
        this.firstItem = firstItem;
    }

    public SearchResultModel() {
    }

    public String getOriginalTerm() {
        return originalTerm;
    }

    public void setOriginalTerm(String originalTerm) {
        this.originalTerm = originalTerm;
    }

    public int getCurrentResultCount() {
        return currentResultCount;
    }

    public void setCurrentResultCount(int currentResultCount) {
        this.currentResultCount = currentResultCount;
    }

    public int getTotalResultCount() {
        return totalResultCount;
    }

    public void setTotalResultCount(int totalResultCount) {
        this.totalResultCount = totalResultCount;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public ArrayList<ProductModel> getResults() {
        return results;
    }

    public void setResults(ArrayList<ProductModel> results) {
        this.results = results;
    }

    public SearchResultModel setModelFromJSONObj(JSONObject jsonObj) {
        if(jsonObj!=null) {
            try {
                if (jsonObj.has("originalTerm")) {
                    setOriginalTerm(jsonObj.getString("originalTerm"));
                }
                if (jsonObj.has("currentResultCount")) {
                    setCurrentResultCount(Integer.parseInt(jsonObj.getString("currentResultCount")));
                }
                if (jsonObj.has("totalResultCount")) {
                    setTotalResultCount(Integer.parseInt(jsonObj.getString("totalResultCount")));
                }
                if (jsonObj.has("term")) {
                    setTerm(jsonObj.getString("term"));
                }
                if (jsonObj.has("results")) {
                    JSONArray jsonArray = jsonObj.getJSONArray("results");
                    ArrayList<ProductModel> prodArrayList = new ArrayList<>();
                    int size = jsonArray.length();
                    for(int i = 0; i<size; i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        ProductModel model = new ProductModel();
                        if(obj.has("brandName")) {
                            model.setBrandName(obj.getString("brandName"));
                        }
                        if(obj.has("thumbnailImageUrl")) {
                            model.setThumbnailImageUrl(obj.getString("thumbnailImageUrl"));
                        }
                        if(obj.has("productId")) {
                            model.setProductId(obj.getString("productId"));
                        }
                        if(obj.has("originalPrice")) {
                            model.setOriginalPrice(obj.getString("originalPrice"));
                        }
                        if(obj.has("styleId")) {
                            model.setStyleId(obj.getString("styleId"));
                        }
                        if(obj.has("colorId")) {
                            model.setColorId(obj.getString("colorId"));
                        }
                        if(obj.has("price")) {
                            model.setPrice(obj.getString("price"));
                        }
                        if(obj.has("percentOff")) {
                            model.setPercentOff(obj.getString("percentOff"));
                        }
                        if(obj.has("productUrl")) {
                            model.setProductUrl(obj.getString("productUrl"));
                        }
                        if(obj.has("productName")) {
                            model.setProductName(obj.getString("productName"));
                        }
                        URL url = new URL(model.getThumbnailImageUrl());
                        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        model.setImageBitMap(bmp);
                        prodArrayList.add(model);
                    }
                    setResults(prodArrayList);
                    if(size>1) {
                        setFirstItem(prodArrayList.get(0));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
