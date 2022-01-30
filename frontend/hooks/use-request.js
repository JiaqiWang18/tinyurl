import { useState } from "react";
import axios from "axios";

export default ({ url, method, body, onSuccess }) => {
  const [errors, setErrors] = useState(null);
  const [isFetching, setIsFetching] = useState(false);
  const doRequest = async (props = {}) => {
    try {
      setErrors(null);
      setIsFetching(true);
      const response = await axios[method](url, { ...body, ...props });
      setIsFetching(false);
      if (onSuccess) {
        onSuccess(response.data);
      }
      return response.data;
    } catch ({ response }) {
      setIsFetching(false);
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
