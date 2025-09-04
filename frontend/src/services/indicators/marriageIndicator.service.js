import api from "../api";

export const fetchMarriageCountByYear = () =>
  api.get("/analyst/indicators/marriage-count-by-year").then(res => res.data);

