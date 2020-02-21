package com.example.zhihunews.model

import com.example.zhihunews.service.Story
import com.example.zhihunews.service.TopStory

fun getBannerPictures(list:MutableList<TopStory>)=
     ArrayList<String>().also {
        for (picture in list){
            it.add(picture.image)
        }
    }


fun getBannerPicturesTitle(list:MutableList<TopStory>)=
    ArrayList<String>().also {
        for (picture in list){
            it.add(picture.title)
        }
    }

fun getBannerPicturesHint(list:MutableList<TopStory>)=
    ArrayList<String>().also {
        for (picture in list){
            it.add(picture.hint)
        }
    }

fun getBannerPicturesUrl(list:MutableList<TopStory>)=
    ArrayList<String>().also {
        for (picture in list){
            it.add(picture.url)
        }
    }

fun getBannerPicturesFuzzyColor(list:MutableList<TopStory>)=
    ArrayList<String>().also {
        for (picture in list){
            it.add(picture.image_hue)
        }
    }

