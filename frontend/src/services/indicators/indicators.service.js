import api from "../api";

export const fetchMarriageCountByYear = () =>
  api.get("/analyst/indicators/marriage-count-by-year").then(res => res.data);

export const fetchDivorceCountByYear = () =>
    api.get("/analyst/indicators/divorce-count-by-year").then(res => res.data);
