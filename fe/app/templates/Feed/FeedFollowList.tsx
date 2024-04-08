// 팔로우 팔로잉 리스트 목록 보여주는 페이지
import colors from "@/assets/color.js";
import React, { useCallback, useState, useEffect } from "react";
import {
  StyleSheet,
  Text,
  View,
  Button,
  SafeAreaView,
  Pressable,
} from "react-native";

import MenuBarCompo from "@/components/MenuBarCompo";
import SearchBoxCompo from "@/components/SearchBoxCompo";
import { ScrollView, FlatList } from "react-native-gesture-handler";
import { fetchFollowing, fetchFollower, fetchUserInfo } from "@/api/feed";
import ProfileCompo from "@/components/ProfileCompo";

export default function FeedFollowList() {
  // 어떤 카테고리가 선택되었는지를 표시
  const [isSelected, setIsSelected] = useState(0);
  const indexes = ["팔로워", "팔로잉"];
  const [followerList, setFollowerList] = useState([]);
  const [followingList, setFollowingList] = useState([]);
  const [userInfo, setUserInfo] = useState([]);
  const [userLevel, setUserLevel] = useState(0);

  // 팔로워 목록 가져오기
  useEffect(() => {
    //userId 임의로 9990로. 이후 recoil에서 가져와야 함.
    fetchFollower(9990).then((response) => {
      setFollowerList(response.data);
      console.log("FeedFollowList.tsx에서 axois 요청");
      console.log("팔로워 목록 불러오기 성공", response.data);
    });
  }, [isSelected]);

  // 팔로잉 목록 가져오기
  useEffect(() => {
    //userId 임의로 9990로. 이후 recoil에서 가져와야 함.
    fetchFollowing(9990).then((response) => {
      setFollowingList(response.data);
      console.log("FeedFollowList.tsx에서 axois 요청");
      console.log("팔로잉 목록 불러오기 성공", response.data);
    });
  }, [isSelected]);

  // 유저 정보 가져오기
  useEffect(() => {
    //userId 임의로 9990로. 이후 recoil에서 가져와야 함.
    fetchUserInfo(9990).then((response) => {
      setUserInfo(response.data);
      console.log("FeedFollowList.tsx에서 axois 요청");
      console.log("유저 정보 불러오기 성공", response.data);
      setUserLevel(response.data.level);
    });
  }, []);

  // 팔로워 또는 팔로잉 목록을 업데이트하고 화면에 반영
  const updateFollowList = (tabIndex) => {
    if (tabIndex === 0) {
      fetchFollower(9990).then((response) => {
        setFollowerList(response.data);
      });
    } else {
      fetchFollowing(9990).then((response) => {
        setFollowingList(response.data);
      });
    }
  };

  return (
    <View style={styles.container}>
      <MenuBarCompo title="친구보기" />
      <View style={{ paddingHorizontal: 22 }}>
        {/* ============인덱스 컴포넌트==== */}
        <View style={styles.indexLineWrapper}>
          <View style={styles.indexWrapper}>
            {indexes.map((index, idx) => {
              return isSelected === idx ? (
                <Pressable
                  key={idx}
                  style={styles.selectedIndex}
                  onPress={() => {
                    setIsSelected(idx);
                    updateFollowList(idx); // 탭 변경 시 팔로워 목록 업데이트
                  }}
                >
                  <Text style={styles.selectedText}>{index}</Text>
                </Pressable>
              ) : (
                <Pressable
                  key={idx}
                  style={styles.unselectedIndex}
                  onPress={() => {
                    setIsSelected(idx);
                    updateFollowList(idx);
                  }}
                >
                  <Text style={styles.unselectedText}>{index}</Text>
                </Pressable>
              );
            })}
          </View>
        </View>
        {/* ======================= */}
      </View>
      {/* //팔로워 팔로잉 목록 렌더링하기 */}
      {isSelected === 0 ? (
        <FlatList
          data={followerList}
          renderItem={({ item }) => {
            // 해당 사용자가 followingList에 있는지 확인
            const isFollowing = followingList.some(
              (following) => following.followingId === item.followerId,
            );
            // association 설정
            const association = isFollowing ? "following" : "unfollowing";

            return (
              <ProfileCompo
                image={item.followerProfile}
                nickname={item.followerName}
                association={association}
                rank={userLevel}
                userId={item.followerId}
                isSelected={isSelected}
              />
            );
          }}
          keyExtractor={(item) => item.followId}
        />
      ) : (
        <FlatList
          data={followingList}
          renderItem={({ item }) => (
            <ProfileCompo
              image={item.followingProfile}
              nickname={item.followingName}
              rank={userLevel}
              association={"following"}
              userId={item.followingId}
              isSelected={isSelected}
            />
          )}
          keyExtractor={(item) => item.followId}
        />
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    // backgroundColor: "yellow",
  },
  indexLineWrapper: {
    flexDirection: "row",
    width: "100%",
    justifyContent: "space-between",
    alignItems: "center",
  },
  indexWrapper: {
    height: 60,
    paddingTop: 13,
    flexDirection: "row",
    gap: 10,
    alignItems: "flex-start",
    justifyContent: "flex-start",
  },
  selectedIndex: {
    paddingVertical: 5,
    paddingHorizontal: 5,
    borderBottomWidth: 2,
    borderBottomColor: colors.darkbrown,
  },
  unselectedIndex: {
    paddingVertical: 5,
    paddingHorizontal: 5,
    borderBottomColor: colors.darkbrown,
  },
  selectedText: {
    fontFamily: "font6",
    fontSize: 16,
    color: colors.darkbrown,
    textAlign: "center",
  },
  unselectedText: {
    fontFamily: "font6",
    fontSize: 16,
    color: "#9B937A",
    textAlign: "center",
  },
  routingPage: {
    width: "100%",
    height: "100%",
    backgroundColor: "teal",
  },
});
