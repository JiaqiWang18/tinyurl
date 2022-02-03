import * as React from "react";
import Drawer from "@mui/material/Drawer";
import { Divider } from "@mui/material";
import useRequest from "../hooks/use-request";

interface UrlsSideBar {
  open: boolean;
  setOpen: Function;
}

export default function UrlsSideBar({ open, setOpen }: UrlsSideBar) {
  const [urls, setUrls] = React.useState<
    {
      hash: string;
      originalUrl: string;
      expireDate: string;
      username?: string;
    }[]
  >([]);

  const { doRequest, errors, isFetching } = useRequest({
    url: "http://127.0.0.1:88/user/urls",
    body: {},
    method: "post",
    onSuccess: (data: any) => {
      setUrls(data.data);
    },
  });

  React.useEffect(() => {
    if (!open) {
      return;
    }
    if (localStorage.getItem("token") !== null) {
      doRequest();
    } else {
      if (localStorage.getItem("localUrls") !== null) {
        // @ts-ignore
        setUrls(JSON.parse(localStorage.getItem("localUrls")));
      }
    }
  }, [open]);

  const renderedUrls = urls.map((url) => (
    <div key={url.hash} className="my-2">
      <div className="row">
        <div className="col">
          <h3>
            <a href={`${location.host + "/" + url.hash}`}>
              {location.host + "/" + url.hash}
            </a>
          </h3>
        </div>
        <div className="col">
          <p className="text-muted" style={{ textAlign: "right" }}>
            Expires: {url.expireDate}
          </p>
        </div>
      </div>

      <a href={`${url.originalUrl}`} style={{ color: "blue" }}>
        {url.originalUrl}
      </a>
      <Divider style={{ color: "black", width: "100%" }} />
    </div>
  ));

  return (
    <div>
      <React.Fragment>
        <Drawer anchor={"right"} open={open} onClose={() => setOpen(false)}>
          <div className="side-bar-container mt-5">
            <div className="container-fluid mt-3">
              <div className="d-flex flex-column">
                <Divider style={{ color: "black", width: "100%" }} />
                <h3 className="mx-auto mt-2">
                  {urls[0] !== undefined &&
                    urls[0].username &&
                    urls[0].username + "'s"}{" "}
                  URLs
                </h3>
                <Divider style={{ color: "black", width: "100%" }} />
                {errors}
                {renderedUrls}
              </div>
            </div>
          </div>
        </Drawer>
      </React.Fragment>
    </div>
  );
}
