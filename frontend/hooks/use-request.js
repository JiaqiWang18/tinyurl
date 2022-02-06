/* eslint-disable import/no-anonymous-default-export */
import { useState } from "react";
import axios from "axios";
import { useDispatch } from "react-redux";
import { setLogIn } from "../state/slices/loginSlice";
import Router from "next/router";

export default ({ url, method, body, onSuccess, onError }) => {
  const dispatch = useDispatch();

  const [errors, setErrors] = useState(null);
  const [isFetching, setIsFetching] = useState(false);
  const doRequest = async (props = {}) => {
    try {
      setErrors(null);
      setIsFetching(true);
      const response = await axios[method](
        url,
        {
          ...body,
          ...props,
        },
        {
          headers: {
            Authorization:
              localStorage.getItem("token") !== null
                ? `Bearer ${localStorage.getItem("token")}`
                : null,
          },
        }
      );
      setIsFetching(false);
      if (onSuccess) {
        onSuccess(response.data);
      }
      return response.data;
    } catch ({ response }) {
      setIsFetching(false);
      if (response.status === 401) {
        localStorage.removeItem("token");
        dispatch(setLogIn(false));
        Router.push({
          pathname: "/login",
          query: {
            message: response.data.message,
            type: "error",
          },
        });
      }
      if (onError) {
        onError(response);
      }
      setErrors(
        <div className="alert alert-danger mt-2">
          <h4>Ooops...</h4>
          <ul className="my-0">
            <li key={response.data.message}>{response.data.message}</li>
          </ul>
        </div>
      );
    }
  };
  return { doRequest, errors, isFetching };
};
