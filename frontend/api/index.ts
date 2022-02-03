const api =
  process.env.NODE_ENV === "development"
    ? "http://localhost:88"
    : "http://" + location.host;
export default api;
