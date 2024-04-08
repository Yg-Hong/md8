// 나중에 URL 확정되면 사용할 공통 주소
// import axios from "./instance";

// 임시로 사용할 axios
import axios from "axios";
const baseURL = "http://j10a208a.p.ssafy.io:8080/api";

// 유저 정보 조회
const getUserInfo = async (userId) => {
  try {
    const response = await axios({
      method: "get",
      url: `${baseURL}/users/${userId}`,
    });
    console.log("axios에서 정보 조회 넘어옴");
    return response.data;
  } catch (error) {
    console.log("axios에서 유저 정보 조회 실패: ", error);
  }
};

// 유저 정보 수정
const updateUserInfo = async (
  userId,
  nickName,
  ageDetailCodeId,
  time,
  distance,
) => {
  try {
    const formData = new FormData();
    formData.append("nickName", nickName);
    formData.append("ageDetailCodeId", ageDetailCodeId ? ageDetailCodeId : 1);
    formData.append("time", time);
    formData.append("distance", distance);

    const response = await axios.patch(`${baseURL}/users/${userId}`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });

    console.log("유저 정보 수정됨!!!");
    return response.data;
  } catch (error) {
    console.log("유저 정보 수정 실패:", error);
  }
};

// 로그아웃
const logout = async (userId) => {
  try {
    const response = await axios({
      method: "post",
      url: `${baseURL}/users/${userId}`,
      params: {
        userId: userId,
      },
    });
    console.log("axios 로그아웃 성공");
    return response.data;
  } catch (error) {
    console.log("axios에서 로그아웃 실패: ", error);
  }
};

export { getUserInfo, updateUserInfo, logout };
