import React, { useState, useEffect } from "react";
import { View, Text, StyleSheet, Image, TouchableOpacity } from "react-native";
import RankFirst from "@icons/RankFirst";
import RankSecond from "@icons/RankSecond";
import RankThird from "@icons/RankThird";
import RankFourth from "@icons/RankFourth";
import ThreePoints from "@icons/ThreePoints";
import colors from "@/assets/color";
import AlertModalCompo from "./AlertModalCompo";
import {
  fetchFollower,
  fetchFollowing,
  follow,
  unfollow,
  blockUser,
  deleteFeed,
  updateFeed,
} from "@/api/feed";

interface ProfileCompoProps {
  userId: number;
  image: string;
  nickname: string;
  association: string;
  rank: number;
  feedId?: number;
}
/**
 * userId: 프로필 유저의 userId
 * image : 프로필 이미지 주소, (기본적으로 카카오 프로필 이미지)
 * nickname : 닉네임 (기본적으로 카카오 이름)
 * association : 관계(myself :본인, following : 팔로잉, unfollowing : 팔로우 안함)
 * rank : 순위
 * @param user
 * @returns
 */
const ProfileCompo = (user: ProfileCompoProps) => {
  const [alertModalVisiable, setAlertModalVisiable] = useState(false);

  const { association } = user;
  const [isFollowing, setIsFollowing] = useState(association === "following");

  // const [isFollowing, setIsFollowing] = useState(
  //   user.association === "following",
  // ); // 팔로잉 여부 상태
  const [isBlocked, setIsBlocked] = useState(false); // 차단 여부 상태

  useEffect(() => {
    setIsFollowing(association === "following");
  }, [association]);

  const onPressProfileImage = () => {
    // todo : 프로필 이미지 클릭 시 이벤트
    // 핀 컴포넌트 개발이 완료되면 이벤트 추가
    console.log("프로필 이미지 클릭");
  };

  const onPressFollowingButton = async () => {
    // todo : 팔로잉 버튼 클릭 시 이벤트
    // 해당 유저를 언팔로우하도록 axios 호출
    // (association을 unfollowing으로 변경)
    // 팔로우 버튼으로 변경
    try {
      const userId = 9990;
      const targetUserId = user.userId;
      const index = user.isSelected;
      await unfollow(userId, targetUserId, index);
      console.log("언팔 성공");
      await fetchFollowing(9990);
      await fetchFollower(9990);
      setIsFollowing(false);
    } catch (error) {
      console.error("언팔 실패", error);
    }
  };

  const onPressFollowButton = async () => {
    try {
      // follwerId는 로그인한 userId 임의로 설정한 값 todo: 리코일에서 가져오기
      const followerId = 9990;
      const followingId = user.userId;
      console.log("================팔로잉아이디...");
      console.log(followingId);
      await follow(followerId, followingId);
      console.log("팔로우 성공");
      // association을 true로 만들기
      await fetchFollowing(9990);
      await fetchFollower(9990);
      setIsFollowing(true);
    } catch (error) {
      console.error("팔로우 실패", error);
    }
  };

  const onPressThreePoints = () => {
    setAlertModalVisiable(true);
    console.log("게시글 수정/삭제/차단 버튼 클릭");
  };

  const BlockUser = async () => {
    try {
      const userId = 9990;
      const targetUserId = user.userId;
      const index = user.isSelected;
      await blockUser(userId, targetUserId, index);
      console.log("블락유저 성공");
      setIsBlocked(true);
    } catch (error) {
      console.error("블락유저 에러:", error);
    }
  };
  // 차단 상태일 때 렌더링할 내용
  if (isBlocked) {
    return null; // 차단된 유저는 화면에서 제거됨
  }

  // ================================
  // const DeletePost = async () => {
  //   try {
  //     // const FeedId=
  //     await deleteFeed(FeedId);
  //     console.log("피드 삭제 성공");
  //   } catch (error) {
  //     console.log("피드 삭제 에러", error);
  //   }
  // };

  // const UpdatePost = async () => {
  //   try {
  //     // const FeedId=
  //     // await deleteFeed(FeedId);
  //     console.log("피드 수정 성공");
  //   } catch (error) {
  //     console.log("피드 수정 에러", error);
  //   }
  // };
  // ============================

  return (
    <View style={styles.rootContainer}>
      <View style={styles.profileContainer}>
        <TouchableOpacity
          style={styles.imageContainer}
          onPress={onPressProfileImage}
        >
          <Image
            style={styles.image}
            source={{
              uri: user.image,
            }}
          />
        </TouchableOpacity>
        <View style={styles.nicknameContainer}>
          <Text style={styles.nicknameTextContainer}>{user.nickname}</Text>
        </View>
        <View style={styles.rankContainer}>
          {/* todo: 일단 inline으로 넣음 */}
          {user.rank === 1 ? (
            <RankFirst width={23} height={23} />
          ) : user.rank === 2 ? (
            <RankSecond width={23} height={23} />
          ) : user.rank === 3 ? (
            <RankThird width={23} height={23} />
          ) : user.rank === 4 ? (
            <RankFourth width={23} height={23} />
          ) : (
            <></>
          )}
        </View>
      </View>
      {/* todo: 본인일 때 프로필 랜더링 조건 다시 생각할 것 */}
      {user.association === "myself" ? (
        <View style={styles.buttonContainer}>
          <TouchableOpacity
            style={styles.threePointsButtonContainer}
            onPress={() => {
              setAlertModalVisiable(true);
              console.log("나일때");
            }}
          >
            <ThreePoints />
          </TouchableOpacity>
        </View>
      ) : (
        // user.association === "following" ?
        // isFollowing ?
        <View style={styles.buttonContainer}>
          {isFollowing ? ( // isFollowing 값에 따라 팔로잉 또는 팔로우 버튼을 표시
            <TouchableOpacity
              style={styles.followButtonContainer}
              onPress={onPressFollowingButton}
            >
              <Text style={styles.unfollowTextContainer}>{"팔로잉"}</Text>
            </TouchableOpacity>
          ) : (
            <TouchableOpacity
              style={styles.unfollowButtonContainer}
              onPress={onPressFollowButton}
            >
              <Text style={styles.followTextContainer}>{"팔로우"}</Text>
            </TouchableOpacity>
          )}
          <TouchableOpacity
            style={styles.threePointsButtonContainer}
            onPress={onPressThreePoints}
          >
            <ThreePoints />
          </TouchableOpacity>
        </View>
      )}
      <AlertModalCompo
        alertModalVisiable={alertModalVisiable}
        setAlertModalVisiable={setAlertModalVisiable} // 모달 표시 상태 변경 함수 전달
        title={
          user.association === "myself"
            ? "게시글을 삭제하시겠습까?"
            : "유저를 차단하시겠습니까?"
        } // 모달 제목
        textWhite={user.association === "myself" ? "수정" : "취소"}
        textGreen={user.association === "myself" ? "삭제" : "차단"}
        onPressCancel={() => {
          // 1. myself이면 게시글 수정, 아니면 취소 기능
          // todo: 게시글 수정!
          user.association === "myself" ? UpdatePost() : null;
          setAlertModalVisiable(false);
        }}
        onPressConfirm={() => {
          // todo: 게시글 삭제
          // 1. myself이면 게시글 삭제, 아니면 차단
          user.association === "myself" ? DeletePost() : BlockUser();
          setAlertModalVisiable(false);
        }}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  rootContainer: {
    width: "100%",
    paddingTop: 5,
    paddingLeft: 6,
    paddingRight: 6,
    paddingBottom: 5,
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
  },
  profileContainer: {
    flexDirection: "row",
    alignItems: "center",
    rowGap: 7,
    columnGap: 7,
  },
  imageContainer: {
    //todo: 이미지를 추가하면서 수정 필요
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
  },
  image: {
    width: 60,
    height: 60,
    borderRadius: 50,
    resizeMode: "cover",
  },
  nicknameContainer: {
    flexDirection: "column",
    alignItems: "center",
    rowGap: 2,
    columnGap: 2,
  },
  nicknameTextContainer: {
    color: colors.darkbrown,
    fontSize: 20,
    fontFamily: "font5",
  },
  rankContainer: {
    flexDirection: "row",
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
  },
  buttonContainer: {
    flexDirection: "row",
    paddingTop: 8,
    paddingLeft: 9,
    paddingRight: 9,
    paddingBottom: 8,
    alignItems: "center",
    rowGap: 5,
    columnGap: 5,
  },
  unfollowButtonContainer: {
    flexDirection: "row",
    paddingTop: 1,
    paddingLeft: 15,
    paddingRight: 15,
    paddingBottom: 1,
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
    borderRadius: 8,
    backgroundColor: colors.green,
  },
  unfollowTextContainer: {
    color: colors.darkbrown,
    textAlign: "center",
    fontFamily: "font6",
    fontSize: 12,
    lineHeight: 24,
  },
  followButtonContainer: {
    flexDirection: "row",
    paddingTop: 1,
    paddingLeft: 15,
    paddingRight: 15,
    paddingBottom: 1,
    alignItems: "center",
    rowGap: 10,
    columnGap: 10,
    borderRadius: 8,
    backgroundColor: colors.lightbeige,
  },
  followTextContainer: {
    color: "rgba(255, 255, 255, 1)",
    textAlign: "center",
    fontFamily: "font6",
    fontSize: 12,
    lineHeight: 24,
  },
  threePointsButtonContainer: {
    flexDirection: "row",
    width: 34,
    padding: 5,
    justifyContent: "center",
    alignItems: "center",
  },
});

export default ProfileCompo;
