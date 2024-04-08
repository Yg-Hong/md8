import colors from "@/assets/color.js";
import React, { useCallback, useState, useEffect } from "react";
import {
  StyleSheet,
  Text,
  View,
  Button,
  SafeAreaView,
  Image,
  TouchableOpacity,
} from "react-native";

import MenuBarCompo from "@/components/MenuBarCompo";
import Plus from "@icons/Plus";
import SearchBoxCompo from "@/components/SearchBoxCompo";
import ProfileCompo from "@/components/ProfileCompo";
import CardCompo from "@/components/Feed/CardCompo";
import { ScrollView } from "react-native-gesture-handler";
import { fetchFeedDetail } from "@/api/feed";
import Swiper from "react-native-swiper";
import TrackingInfo from "@icons/TrackingInfo";
import { useNavigation } from "@react-navigation/native";

export default function FeedDetail({ route }) {
  const navigation = useNavigation();
  const {
    profileImage,
    nickname,
    association,
    rank,
    content,
    hashtags,
    images,
    userId,
    feedId,
    // trackingId,
  } = route.params;

  const gotoTrackingInfo = () => {
    navigation.navigate("TrackingInfoDetail", { trackingId: trackingId });
  };

  return (
    <View style={styles.container}>
      <MenuBarCompo title="상세 피드 보기" />
      <View style={styles.detailContainer}>
        <ProfileCompo
          image={profileImage}
          nickname={nickname}
          association={association}
          rank={rank}
          userId={userId}
          feedId={feedId}
        />
        <Swiper
          style={{ height: 600 }}
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

        <View style={styles.textContainer}>
          <View
            style={{ flexDirection: "row", justifyContent: "space-between" }}
          >
            <Text style={{ color: colors.darkbrown, fontFamily: "font5" }}>
              {content}
            </Text>
            {/* ============================== */}
            <TouchableOpacity onPress={gotoTrackingInfo}>
              <TrackingInfo />
            </TouchableOpacity>
            {/* =================================== */}
          </View>
          <Text style={{ color: colors.beige, fontFamily: "font3" }}>
            {hashtags}
          </Text>
        </View>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "white",
  },
  detailContainer: {
    flex: 1,
    // backgroundColor: "yellow",
    paddingHorizontal: 20,
  },
  imgContainer: {
    backgroundColor: "black",
    width: "100%",
    height: 300,
    resizeMode: "cover",
  },
  textContainer: {
    // backgroundColor: "red",
    padding: 15,
    gap: 20,
  },
});
