// 나중에 URL 확정되면 사용할 공통 주소
// import axios from "./instance";

// 임시로 사용할 axios
import axios from "axios";
import mime from "mime";
import * as Expo from "expo";
import * as FileSystem from "expo-file-system";

const baseURL = "http://j10a208a.p.ssafy.io:8082/api";

// 경로 목록 조회
const getAllTracks = async () => {
  try {
    const response = await axios({
      method: "get",
      url: `${baseURL}/v1/tracking`,
    });
    return response.data;
  } catch (error) {
    console.log(error);
  }
};

// 피드 생성 시 산책로 선택
const selectTracking = async (userId, page, size) => {
  try {
    const nextPage = page + 1;
    const response = await axios({
      method: "get",
      url: `${baseURL}/v1/tracking/by-creator?userId=${userId}&page=${nextPage}&size=${size}`,
    });
    return response.data;
  } catch (error) {
    console.log(error);
  }
};

// 피드 생성
const createFeed = async (userId, trackingId, content, hashtagList, imgs) => {
  try {
    const formData = new FormData();
    formData.append("userId", userId);
    formData.append("trackingId", trackingId);
    formData.append("content", content);
    formData.append("hashtagList", hashtagList);

    // 이미지들을 순회하며 FormData에 추가하고 서버로 전송
    imgs.forEach(async (img) => {
      const uri = img;
      const type = mime.getType(img);
      const name = img.split("/").pop();

      formData.append("fileList", {
        uri: uri,
        name: name,
        type: type,
      });

      console.log(formData);
      try {
        // FormData에 이미지를 추가한 후에 서버로 전송
        const response = await axios.post(
          `http://j10a208a.p.ssafy.io:8081/api/feeds`,
          formData,
          {
            headers: {
              "Content-type": "multipart/form-data",
            },
          },
        );
        console.log("response뽑기");
        console.log(response);
      } catch (error) {
        console.log(error);
      }
    });
  } catch (error) {
    console.log(error);
  }
};

// 회원 정보 조회
const fetchUserInfo = async (userId) => {
  try {
    const response = await axios({
      method: "get",
      url: `http://j10a208a.p.ssafy.io:8080/api/users/${userId}`,
    });
    return response.data;
  } catch (error) {
    console.log(error);
  }
};

// 팔로잉 목록 조회(본인이 팔로잉하고 있는 회원목록을 조회)
const fetchFollowing = async (userId) => {
  try {
    const response = await axios({
      method: "get",
      url: `http://j10a208a.p.ssafy.io:8080/api/follows/following/${userId}`,
    });
    return response.data;
  } catch (error) {
    console.log("팔로잉 조회 에러", error);
  }
};

//팔로워 목록 조회(본인을 팔로우하고 있는 회원목록을 조회)
const fetchFollower = async (userId) => {
  try {
    const response = await axios({
      method: "get",
      url: `http://j10a208a.p.ssafy.io:8080/api/follows/follower/${userId}`,
    });
    return response.data;
  } catch (error) {
    console.log("팔로워조회에러", error);
  }
};

// 팔로우 등록
const follow = async (followerId, followingId) => {
  try {
    const data = {
      followerId: followerId,
      followingId: followingId,
    };
    const response = await axios.post(
      `http://j10a208a.p.ssafy.io:8080/api/follows`,
      data,
    );
    console.log("팔로우 성공?", response.data);
    return response.data;
  } catch (error) {
    console.error("팔로우 에러", error);
    throw error;
  }
};

//팔로워 삭제(언팔로우)
const unfollow = async (userId, targetUserId, index) => {
  const path = index == 0 ? "follower" : "following";
  const data = {
    userId: userId,
    targetUserId: targetUserId,
  };
  try {
    const response = await axios.delete(
      `http://j10a208a.p.ssafy.io:8080/api/follows/${path}`,
      { data },
    );
    console.log("언팔로우 성공?", response.data);
    return response.data;
  } catch (error) {
    console.error("언팔로우 에러?", error);
  }
};

// 유저 차단
const blockUser = async (userId, targetUserId, index) => {
  const path = index == 0 ? "follower" : "following";
  const data = {
    userId: userId,
    targetUserId: targetUserId,
  };
  try {
    const response = await axios.patch(
      `http://j10a208a.p.ssafy.io:8080/api/follows/block/${path}`,
      data,
    );
    console.log("차단 성공?", response.data);
    return response.data;
  } catch (error) {
    console.error("차단 에러:", error);
  }
};

// ===============================
// 피드 삭제
const deleteFeed = async (feedId) => {
  try {
    const response = await axios.patch(
      `http://j10a208a.p.ssafy.io:8081/api/feeds/remove/${feedId}`,
    );
    console.log("피드 삭제 성공");
  } catch (error) {
    console.error("피드삭제 에러:", error);
  }
};

//피드 수정
const updateFeed = async (feedId) => {
  const data = {};
  // {
  //   "request": {
  //     "content": "string",
  //     "hashtagList": [
  //       {
  //         "hashtagId": 0,
  //         "hashtagName": "string"
  //       }
  //     ]
  //   },
  //   "imgs": [
  //     "string"
  //   ]
  // }
  try {
    const response = await axios.patch(
      `http://j10a208a.p.ssafy.io:8081/api/feeds/${feedId}`,
      data,
    );
    console.log("피드 수정 성공");
  } catch (error) {
    console.error("피드 수정 에러:", error);
  }
};
// ===========================================

//전체 피드 조회
const fetchFeeds = async (userId) => {
  try {
    const response = await axios({
      method: "get",
      url: `http://j10a208a.p.ssafy.io:8081/api/feeds/home/${userId}`,
    });
    return response.data;
  } catch (error) {
    console.log(error);
  }
};

//피드 디테일 조회
const fetchFeedDetail = async (feedId) => {
  try {
    const response = await axios({
      method: "get",
      url: `http://j10a208a.p.ssafy.io:8081/api/feeds/${feedId}`,
    });
    return response.data;
  } catch (error) {
    console.log("피드 디테일 에러", error);
  }
};

//유저 검색
const searchUser = async (userId, nickName) => {
  try {
    const response = await axios.get(
      `http://j10a208a.p.ssafy.io:8080/api/users/search/${userId}`,
      { params: { nickName: nickName } },
    );
    return response.data;
  } catch (error) {
    console.log("유저 검색 에러", error);
  }
};

// 해시태그 검색
const searchHashtag = async (hashtag) => {
  try {
    const response = await axios.get(
      `http://j10a208a.p.ssafy.io:8081/api/feeds/search/hashtags`,
      { params: { hashtag: hashtag } },
    );
    return response.data;
  } catch (error) {
    console.log("해시태그 검색 에러", error);
  }
};
export {
  getAllTracks,
  selectTracking,
  createFeed,
  fetchUserInfo,
  fetchFollowing,
  fetchFollower,
  follow,
  unfollow,
  blockUser,
  deleteFeed,
  updateFeed,
  fetchFeeds,
  fetchFeedDetail,
  searchUser,
  searchHashtag,
};
