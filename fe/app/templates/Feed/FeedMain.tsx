import colors from "@/assets/color.js";
import React, { useCallback, useState, useEffect } from "react";
import {
  StyleSheet,
  Text,
  View,
  Button,
  SafeAreaView,
  FlatList,
  TouchableOpacity,
  Keyboard,
  ImageBackground,
} from "react-native";
import { useNavigation } from "@react-navigation/native";
import MenuBarCompo from "@/components/MenuBarCompo";
import Plus from "@icons/Plus";
import Search from "@icons/Search";
import TrackingInfo from "@icons/TrackingInfo";
import CardCompo from "@/components/Feed/CardCompo";
import { ScrollView } from "react-native-gesture-handler";
import { fetchFeeds, fetchUserInfo, fetchFollowing } from "@/api/feed";

export default function FeedMain() {
  const navigation = useNavigation();
  const [feeds, setFeeds] = useState([]);
  const [userInfo, setUserInfo] = useState([]);
  const [userLevel, setUserLevel] = useState(0);

  // 팔로잉하는 유저들 피드 전체
  useEffect(() => {
    fetchFeeds(9990)
      .then((response) => {
        setFeeds(response.data.feedDetailResponse);
      })
      .catch((error) => {
        console.error("Error fetching feeds:", error);
      });
  }, []);

  console.log("======피드 뽑을래");
  console.log(feeds);

  useEffect(() => {
    // 각 피드의 사용자 정보를 가져와서 레벨을 설정합니다.
    feeds.forEach((item) => {
      fetchUserInfo(item.userId)
        .then((response) => {
          // 사용자 정보에서 레벨을 가져와서 설정합니다.
          const level = response.data.level;
          setUserLevel(level);
        })
        .catch((error) => {
          console.log(error);
        });
    });
  }, [feeds]); // feeds가 변경될 때마다 호출

  // console.log(feeds);
  const gotoSearch = () => {
    //검색창 클릭 시
    navigation.navigate("FeedSearch");
  };
  const gotoCreateFeed = () => {
    navigation.navigate("FeedCreate");
  };
  const gotoFollowList = () => {
    navigation.navigate("FeedFollowList");
  };

  // 이미지 파일 URL만을 담은 배열 생성
  const images = feeds.reduce((acc, curr) => {
    return acc.concat(curr.photos.map((photo) => photo.fileUrl));
  }, []);
  // console.log("feeds뽑기");
  // console.log(feeds);

  return (
    <View style={styles.container}>
      <View style={styles.topContainer}>
        <MenuBarCompo title="피드보기" />
        <View style={styles.iconContainer}>
          <TouchableOpacity onPress={gotoSearch}>
            <View style={styles.searchContainer}>
              <Search />
            </View>
          </TouchableOpacity>
          <TouchableOpacity onPress={gotoCreateFeed}>
            <Plus />
          </TouchableOpacity>
        </View>
      </View>
      {/* =============== */}
      {/* <TouchableOpacity onPress={gotoFollowList}>
        <Text>일단 임의로 팔로우 팔로잉 목록 조회하는 버튼 </Text>
      </TouchableOpacity> */}
      {/* =================== */}
      <FlatList
        data={feeds}
        renderItem={({ item }) => (
          <CardCompo
            profileImage={item.profile}
            nickname={item.nickName}
            association={"following"}
            rank={userLevel}
            content={item.content}
            hashtags={item.hashtags
              .map((hashtag) => `#${hashtag.hashtagName}`)
              .join(" ")}
            images={images}
            userId={item.userId}
            feedId={item.feedId}
            trackingId={item.trackingId}
          />
        )}
        keyExtractor={(item) => item.feedId}
        contentContainerStyle={{ paddingHorizontal: 20 }}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "white",
  },
  topContainer: {
    flexDirection: "row",
    alignItems: "center",
    // justifyContent: "space-around",
    // justifyContent: "evenly",
    justifyContent: "space-between",
    paddingRight: 100,
  },
  iconContainer: {
    flexDirection: "row",
  },
  searchContainer: {
    paddingHorizontal: 20,
  },
});
