package com.example.taobaoadressdialog;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Android Studio.
 * User: lishuai
 * Date: 2021/1/28
 * Time: 上午9:58
 */
public class AddressHelper {

    /**
     * 获取数据并排序
     *
     * @param context
     * @return
     */
    public List<ProjectItemBean> getCountryData(Context context) {
        Gson gson = new Gson();
        List<ProjectItemBean> addressCountryBeans = gson.fromJson(getJsonFromAssets(context), new TypeToken<List<ProjectItemBean>>() {
        }.getType());
        return addressCountryBeans;
    }


    /**
     * 按字母处理排序问题
     */
//    public List<ProjectItemBean> sort(List<ProjectItemBean> addressCountryBeans){
//
//        if(null == addressCountryBeans || addressCountryBeans.size()< 0){
//            return null;
//        }
//
//        for (ProjectItemBean mData : addressCountryBeans) {
//            //汉字转换成拼音
//            String countryFirst = getFirstLetterSpell(mData.getName());
//            mData.setFirstLetter(countryFirst);
//
//            if(mData.getChildList() == null || mData.getChildList().size() == 0){
//                continue;
//            }
//            for(ProjectItemBean cityBean : mData.getChildList()){
//
//                String cityFirst = getFirstLetterSpell(cityBean.getName());
//                cityBean.setFirstLetter(cityFirst);
//
//                if(cityBean.getChildList() == null || cityBean.getChildList().size() == 0){
//                    continue;
//                }
//                for(ProjectItemBean areaBean : cityBean.getChildList()){
//
//                    String areaFirst = getFirstLetterSpell(areaBean.getName());
//                    areaBean.setFirstLetter(areaFirst);
//
//                    if(areaBean.getChildList() == null || areaBean.getChildList().size() == 0){
//                        continue;
//                    }
//
//                    for(ProjectItemBean villageBean : areaBean.getChildList()){
//                        String villageFirst = getFirstLetterSpell(villageBean.getName());
//                        villageBean.setFirstLetter(villageFirst);
//                    }
//                    Log.e("areaBean",areaBean.getChildList().toString());
//                    Collections.sort(areaBean.getChildList(), new LettersComparator<ProjectItemBean>());
//                }
//
//                Collections.sort(cityBean.getChildList(), new LettersComparator<ProjectItemBean>());
//
//            }
//            Collections.sort(mData.getChildList(), new LettersComparator<ProjectItemBean>());
//        }
//        Collections.sort(addressCountryBeans, new LettersComparator<ProjectItemBean>());
//        return addressCountryBeans;
//    }

    //按字母排序
    public List<ProjectItemBean> sortListByLetter(List<ProjectItemBean> addressCountryBeans) {

        if (null == addressCountryBeans || addressCountryBeans.size() < 0) {
            return null;
        }

        for (ProjectItemBean mData : addressCountryBeans) {
            //汉字转换成拼音
            String countryFirst = getFirstLetterSpell(mData.getName());
            mData.setFirstLetter(countryFirst);
        }
        Collections.sort(addressCountryBeans, new LettersComparator<ProjectItemBean>());
        return addressCountryBeans;
    }

//    /**
//     * 按字母将二级数据进行分组
//     */
//    public List<CommonHeadSection<ProjectItemBean.ProvinceBean>> getSecondSection(List<ProjectItemBean.ProvinceBean> provinceBeans){
//        List<String> letterRights = getRightLetters(provinceBeans);
//        int headPosition=0;
//        List<CommonHeadSection<ProjectItemBean.ProvinceBean>> list = new ArrayList<>();
//        for (int i = 0; i < letterRights.size(); i++) {
//            headPosition++;
//            CommonHeadSection<ProjectItemBean.ProvinceBean> sectionHeader = new CommonHeadSection<ProjectItemBean.ProvinceBean>(true, letterRights.get(i), headPosition);
//            list.add(sectionHeader);
//            if (provinceBeans.size() != 0) {
//                for (int j = 0; j < provinceBeans.size(); j++) {
//                    if(provinceBeans.get(j).getFirstLetter().equals(letterRights.get(i))) {
//                        headPosition++;
//                        CommonHeadSection<ProjectItemBean.ProvinceBean> addressBean = new CommonHeadSection<ProjectItemBean.ProvinceBean>(provinceBeans.get(j));
//                        list.add(addressBean);
//                    }
//                }
//            }
//        }
//        return list;
//    }
//
//    /**
//     *
//     * 按字母将四级数据进行分组
//     * @param areaBeans
//     * @return
//     */
//    public List<CommonHeadSection<ProjectItemBean.ProvinceBean.CityBean.AreaBean>> getFourthSection(List<ProjectItemBean.ProvinceBean.CityBean.AreaBean> areaBeans){
//        List<String> letterRights = getRightLetters(areaBeans);
//        int headPosition=0;
//        List<CommonHeadSection<ProjectItemBean.ProvinceBean.CityBean.AreaBean>> list = new ArrayList<>();
//        for (int i = 0; i < letterRights.size(); i++) {
//            headPosition++;
//            CommonHeadSection<ProjectItemBean.ProvinceBean.CityBean.AreaBean> sectionHeader = new CommonHeadSection<ProjectItemBean.ProvinceBean.CityBean.AreaBean>(true, letterRights.get(i), headPosition);
//            list.add(sectionHeader);
//            if (areaBeans.size() != 0) {
//                for (int j = 0; j < areaBeans.size(); j++) {
//                    if(areaBeans.get(j).getFirstLetter().equals(letterRights.get(i))) {
//                        headPosition++;
//                        CommonHeadSection<ProjectItemBean.ProvinceBean.CityBean.AreaBean> addressBean = new CommonHeadSection<ProjectItemBean.ProvinceBean.CityBean.AreaBean>(areaBeans.get(j));
//                        list.add(addressBean);
//                    }
//                }
//            }
//        }
//        return list;
//    }
//
//    /**
//     * 获取三级数据
//     * @param cityBeans
//     * @return
//     */
//    public List<CommonHeadSection<ProjectItemBean.ProvinceBean.CityBean>> getThirdSection(List<ProjectItemBean.ProvinceBean.CityBean> cityBeans){
//        List<String> letterRights = getRightLetters(cityBeans);
//        int headPosition=0;
//        List<CommonHeadSection<ProjectItemBean.ProvinceBean.CityBean>> cityList = new ArrayList<>();
//        for (int i = 0; i < letterRights.size(); i++) {
//            headPosition++;
//            CommonHeadSection<ProjectItemBean.ProvinceBean.CityBean> sectionHeader = new CommonHeadSection<ProjectItemBean.ProvinceBean.CityBean>(true, letterRights.get(i), headPosition);
//            cityList.add(sectionHeader);
//            if (cityBeans.size() != 0) {
//                for (int j = 0; j < cityBeans.size(); j++) {
//                    if(cityBeans.get(j).getFirstLetter().equals(letterRights.get(i))) {
//                        headPosition++;
//                        CommonHeadSection<ProjectItemBean.ProvinceBean.CityBean> addressBean = new CommonHeadSection<ProjectItemBean.ProvinceBean.CityBean>(cityBeans.get(j));
//                        cityList.add(addressBean);
//                    }
//                }
//            }
//        }
//        return cityList;
//    }

    /**
     * 读取本地文件
     *
     * @param context
     * @return
     */
    public String getJsonFromAssets(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("地址示例.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private String getFirstLetterSpell(String firstLetter) {
        String pinyin = PinyinUtils.converterToFirstSpell(firstLetter);
        Log.e("pinyin--------->", pinyin);
        if (pinyin.length() > 0) {
            String letters = pinyin.substring(0, 1).toUpperCase();
            Log.e("letters--------->", letters);
            if (letters.matches("[A-Z]")) {
                return letters.toUpperCase();
            } else {
                return "#";
            }
        }
        return "#";
    }

    public <T extends FirstLetterBean> List<String> getRightLetters(List<T> datas) {

        List<String> rightLetters = new ArrayList<>();

        for (FirstLetterBean data : datas) {
            if (!rightLetters.contains(data.getFirstLetter())) {
                rightLetters.add(data.getFirstLetter());
            }
        }
        return rightLetters;
    }

}
