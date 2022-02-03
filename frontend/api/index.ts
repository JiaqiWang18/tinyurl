const api =
  process.env.NODE_ENV === "development"
    ? "http://localhost:88"
    : "https://tinyurl.jackywang.us";
export default api;
