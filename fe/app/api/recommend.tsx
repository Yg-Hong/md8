// 나중에 URL 확정되면 사용할 공통 주소
// import axios from "./instance";

// 임시로 사용할 axios
import axios from "axios";
import { endAsyncEvent } from "react-native/Libraries/Performance/Systrace";
const baseURL = "http://j10a208a.p.ssafy.io:8083/api";

// 추천 산책로 조회
const getRecommendation = async (longitude, latitude, tempTime, tempDist) => {
  try {
    const response = await axios({
      method: "get",
      url: `${baseURL}/recommendation/reco/tracking`,
      params: {
        num: 30,
        lon: longitude,
        lat: latitude,
        time: tempTime,
        dist: tempDist,
      },
    });
    return response.data;
  } catch (error) {
    console.log("getRecommendation 에러", error);
  }
};

// 북마크 등록
const addBookMark = async (userId, trackingId) => {
  try {
    const response = await axios({
      method: "post",
      url: `http://j10a208a.p.ssafy.io:8082/api/v1/bookmark`,
      params: {
        trackingId: trackingId,
        userId: userId,
      },
    });
    // console.log("axios 북마크에 추가");
    return response.data;
  } catch (error) {
    console.log("axios 북마크 등록 에러", error);
  }
};

export { getRecommendation, addBookMark };
