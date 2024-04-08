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
import { fetchFeedDetail, fetchUserInfo, fetchFollowing } from "@/api/feed";
import { useNavigation } from "@react-navigation/native";
import Swiper from "react-native-swiper";
import TrackingInfo from "@icons/TrackingInfo";

export default function FeedDetailSearch({ route }) {
  const { feedId } = route.params;
  // console.log("피드 아이디 뽑", feedId);
  const [feed, setFeed] = useState([]);
  const [userLevel, setUserLevel] = useState(0);
  const [followingList, setFollowingList] = useState([]);
  const [association, setAssociation] = useState("");
  const navigation = useNavigation();

  const gotoTrackingInfo = () => {
    navigation.navigate("TrackingInfoDetail", { trackingId: trackingId });
  };

  // 피드 디테일
  useEffect(() => {
    fetchFeedDetail(feedId)
      .then((response) => {
        // console.log("response.data뽑", response.data);
        setFeed(response.data);
      })
      .catch((error) => {
        console.error("Error fetching feed detail:", error);
      });
  }, []);
  // console.log("====피드 뽑을래================");
  // console.log(feed);
  // console.log("피드아이디뽑을래");
  // console.log(feedId);

  const content = feed.content;
  const image = feed.profile;
  const nickname = feed.nickName;
  const feedUserId = feed.userId;
  const photos = feed.photos || [];

  useEffect(() => {
    // 각 피드의 사용자 정보를 가져와서 레벨을 설정합니다.
    fetchUserInfo(feedUserId)
      .then((response) => {
        // 사용자 정보에서 레벨을 가져와서 설정합니다.
        const level = response.data.level;
        setUserLevel(level);
      })
      .catch((error) => {
        console.log(error);
      });
  }, [feed.userId]);
  // console.log("레벨", userLevel);
  // console.log(feedUserId);

  //팔로잉 목록 불러오기
  useEffect(() => {
    const userId = 9990;
    fetchFollowing(userId)
      .then((response) => {
        // 검색 결과의 userId가 팔로잉 목록에 있는지 확인하고 association 설정
        console.log("following뽑기~~~~~~~~~~", response.data);
        setFollowingList(response.data);
        const isFollowing = followingList.some(
          (following) => following.followingId === feedUserId,
        );
        setAssociation(isFollowing ? "following" : "unfollowing");
      })
      .catch((error) => {
        console.error("Error fetching following list:", error);
      });
  }, [feed.userId]);
  console.log(association);

  return (
    <View style={styles.container}>
      <MenuBarCompo title="상세 피드 보기" />
      <View style={styles.detailContainer}>
        <ProfileCompo
          image={image}
          nickname={nickname}
          association={association}
          rank={userLevel}
          userId={feedUserId}
          feedId={feedId}
        />
        <Swiper
          style={{ height: 600 }}
          showsButtons={false}
          showsPagination={true}
          paginationStyle={{ bottom: 20 }}
        >
          {photos.map((photo) => (
            <View key={photo.id}>
              <Image
                source={{ uri: photo.fileUrl }}
                style={styles.imgContainer}
              />
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
            {feed.hashtags
              ? feed.hashtags
                  .map((hashtag) => `#${hashtag.hashtagName}`)
                  .join(" ")
              : ""}
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
