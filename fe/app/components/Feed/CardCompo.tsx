import React, { Component } from "react";
import { useFonts } from "expo-font";
import { useNavigation } from "@react-navigation/native";
import TrackingInfo from "@icons/TrackingInfo";
import {
  View,
  Image,
  Text,
  StyleSheet,
  Dimensions,
  ScrollView,
  TouchableOpacity,
} from "react-native";
import Swiper from "react-native-swiper";

import ProfileCompo from "../ProfileCompo";
import colors from "@/assets/color";

const CardCompnent = ({
  profileImage,
  nickname,
  association,
  rank,
  content,
  hashtags,
  images,
  userId,
  feedId,
  trackingId,
}) => {
  const navigation = useNavigation();

  const goToFeedDetail = () => {
    // FeedDetail 페이지로 이동
    navigation.navigate("FeedDetail", {
      feedId,
      profileImage,
      nickname,
      association,
      rank,
      content,
      hashtags,
      images,
      userId,
      trackingId,
    });
    console.log("피드 디테일 가기");
  };

  const gotoTrackingInfo = () => {
    navigation.navigate("TrackingInfoDetail", { trackingId: trackingId });
  };

  return (
    <View style={styles.container}>
      <ProfileCompo
        image={profileImage}
        nickname={nickname}
        association={association}
        rank={rank}
        userId={userId}
        feedId={feedId}
      />

      <Swiper
        style={{ height: 300 }}
        showsButtons={false}
        showsPagination={true}
        paginationStyle={{ bottom: 20 }}
      >
        {images.map((image, index) => (
          <View key={index}>
            <Image source={{ uri: image }} style={styles.imgContainer} />
          </View>
        ))}
      </Swiper>
      <TouchableOpacity onPress={goToFeedDetail}>
        <View style={styles.textContainer}>
          <View
            style={{ flexDirection: "row", justifyContent: "space-between" }}
          >
            <Text
              style={{
                color: colors.darkbrown,
                fontFamily: "font5",
              }}
            >
              {content}
            </Text>
            {/* ============================= */}
            <TouchableOpacity onPress={gotoTrackingInfo}>
              <TrackingInfo />
            </TouchableOpacity>
            {/* ============================== */}
          </View>

          <Text
            style={{
              color: colors.beige,
              fontFamily: "font3",
            }}
          >
            {hashtags}
          </Text>
        </View>
      </TouchableOpacity>

      <View
        style={{
          borderBottomColor: colors.darkbrown,
          borderBottomWidth: StyleSheet.hairlineWidth,
        }}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  imgContainer: {
    // backgroundColor: "black",
    width: "100%",
    height: 300,
    resizeMode: "cover",
  },
  textContainer: {
    padding: 10,
    gap: 5,
  },
});
export default CardCompnent;
