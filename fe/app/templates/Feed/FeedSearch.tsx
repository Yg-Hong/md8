import colors from "@/assets/color.js";
import React, { useCallback, useState, useEffect } from "react";
import {
  StyleSheet,
  Text,
  View,
  Pressable,
  Button,
  SafeAreaView,
} from "react-native";

import MenuBarCompo from "@/components/MenuBarCompo";
import SearchBoxCompo from "@/components/SearchBoxCompo";
import { ScrollView, FlatList } from "react-native-gesture-handler";
import IndexingCompo from "@/components/IndexingCompo";
import { searchUser, fetchFollowing, searchHashtag } from "@/api/feed";
import ProfileCompo from "@/components/ProfileCompo";
import FeedGridCompo from "@/components/FeedGridCompo";

export default function FeedSearch() {
  const indexes = ["유저", "해시태그"];
  // 어떤 카테고리가 선택되었는지를 표시
  const [isSelected, setIsSelected] = useState(0);
  const [search, setSearch] = useState("");
  const [userSearchResult, setUserSearchResult] = useState([]);
  const [hashtagSearchResult, setHashtagSearchResult] = useState([]);
  const [followingList, setFollowingList] = useState([]);

  const userId = 9990;
  // 검색어가 변경될 때마다 검색을 수행하는 함수
  useEffect(() => {
    if (isSelected === 0) {
      // 유저를 선택했을 때만 검색 수행
      // 검색어가 비어있지 않은 경우에만 검색 요청
      if (search.trim() !== "") {
        //search가 검색창에 입력된 닉네임
        searchUser(userId, search)
          .then((response) => {
            // 검색 결과 설정
            // console.log("검색결과?", response.data);
            setUserSearchResult(response.data);
          })
          .catch((error) => {
            console.error("Error searching users:", error);
          });
      } else {
        // 검색어가 비어있으면 검색 결과 초기화
        setUserSearchResult([]);
      }
    } else if (isSelected === 1) {
      if (search.trim() !== "") {
        searchHashtag(search)
          .then((response) => {
            console.log(search);
            console.log("해시태그검색결과", response.data);
            console.log(response.data.myFeedThumbnailResponseList);
            setHashtagSearchResult(response.data.myFeedThumbnailResponseList);
          })
          .catch((error) => {
            console.error("Error searching hashtags:", error);
          });
      } else {
        setHashtagSearchResult([]);
      }
    }
    // console.log("결과????????", hashtagSearchResult);

    //팔로잉 목록 불러오기
    fetchFollowing(userId)
      .then((response) => {
        // 검색 결과의 userId가 팔로잉 목록에 있는지 확인하고 association 설정
        setFollowingList(response.data);
      })
      .catch((error) => {
        console.error("Error fetching following list:", error);
      });
  }, [search, isSelected]); // 검색어 또는 인덱스 선택이 변경될 때마다 실행

  // console.log(followingList);
  // console.log(search);
  console.log("해시태그검색결과", hashtagSearchResult);

  return (
    <View style={styles.container}>
      <MenuBarCompo title="검색하기" />
      <SearchBoxCompo
        placeholder="검색어를 입력해주세요"
        value={search}
        onChangeText={setSearch}
      />
      <View style={{ paddingLeft: 37 }}>
        <View style={styles.indexLineWrapper}>
          <View style={styles.indexWrapper}>
            {indexes.map((index, idx) => {
              return isSelected === idx ? (
                <Pressable
                  key={idx}
                  style={styles.selectedIndex}
                  onPress={() => {
                    setIsSelected(idx);
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
                  }}
                >
                  <Text style={styles.unselectedText}>{index}</Text>
                </Pressable>
              );
            })}
          </View>
        </View>
      </View>
      {/* //유저 검색결과 렌더링하기 */}
      {isSelected === 0 ? (
        <FlatList
          data={userSearchResult}
          renderItem={({ item }) => {
            // userId가 followingList에 있는지 확인하여 association 설정
            const association = followingList.find(
              (following) => following.followingId === item.userId,
            )
              ? "following"
              : "unfollowing";
            return (
              <ProfileCompo
                key={item.userId}
                image={item.profile}
                nickname={item.nickName}
                association={association}
                rank={item.level}
                userId={item.userId}
              />
            );
          }}
          keyExtractor={(item) => item.userId.toString()}
        />
      ) : (
        <FeedGridCompo data={hashtagSearchResult} />
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "white",
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
