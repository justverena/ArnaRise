import api from "../api";

export const fetchMarriageCountByYear = (district) =>
  api.get("/analyst/indicators/marriage-count-by-year", { params: { district }}).then(res => res.data);

export const fetchDivorceCountByYear = (district) =>
    api.get("/analyst/indicators/divorce-count-by-year", { params: { district }}).then(res => res.data);

export const fetchAvgAgeByYear = (district) => 
  api.get("/analyst/indicators/marriage-av-age-by-year", { params: { district }}).then(res => res.data);

export const fetchDivorceRatioByYear = (district) =>
  api.get("/analyst/indicators/ratio-divorces-to-marriages-by-year", { params: { district }}).then(res => res.data);
