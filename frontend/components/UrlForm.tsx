import * as React from "react";
import TextField from "@mui/material/TextField";
import useRequest from "../hooks/use-request";
import { Button, CircularProgress } from "@mui/material";
import { Box } from "@mui/system";
import DialogPopup from "./DialogPopup";
import { CopyToClipboard } from "react-copy-to-clipboard";

export default function UrlForm() {
  const [originalUrl, setUrl] = React.useState("");
  const [alias, setAlias] = React.useState<string>("");

  const [dialogOpen, setDialogOpen] = React.useState(false);
  const [shortenedUrl, setShortenedUrl] = React.useState<string>("error");

  const { doRequest, errors, isFetching } = useRequest({
    url: "http://tinyurl.jackywang.us/url/generate",
    method: "post",
    body: {
      original: originalUrl,
      alias,
    },
    onSuccess: (data: any) => {
      setShortenedUrl(location.host + "/" + data.data);
      setDialogOpen(true);
    },
  });

  const generateShortUrl = async () => {
    doRequest();
  };

  const handleDialogClose = () => {
    setDialogOpen(false);
  };

  const renderedDialogContent = (
    <div>
      <CopyToClipboard text={shortenedUrl}>
        <button
          className="btn mt-2"
          data-toggle="tooltip"
          data-placement="bottom"
          title="copy"
        >
          <h5>
            {shortenedUrl} <i className="fas fa-copy"></i>
          </h5>
        </button>
      </CopyToClipboard>
    </div>
  );

  return (
    <div className="justify-content-center align-self-center mx-auto mb-5">
      <div className="form">
        <div className="mb-3">
          <h1 className="">
            TinyURL <i className="fas fa-link"></i>
          </h1>
        </div>
        {isFetching ? (
          <div className="mx-auto my-5">
            <CircularProgress />
          </div>
        ) : (
          <>
            {errors}
            <div className="form-group">
              <TextField
                id="outlined-basic"
                label="Oringal Url"
                variant="standard"
                fullWidth
                value={originalUrl}
                onChange={(e) => {
                  setUrl(e.target.value);
                }}
              />
            </div>
            <div className="form-group my-3">
              <TextField
                id="outlined-basic"
                label="Custom Alias"
                variant="standard"
                value={alias}
                onChange={(e) => {
                  setAlias(e.target.value);
                }}
                fullWidth
              />
            </div>
            <div className="form-group my-1">
              <Box textAlign="center">
                <Button variant="contained" onClick={() => generateShortUrl()}>
                  Generate
                </Button>
              </Box>
            </div>
          </>
        )}
      </div>
      <DialogPopup
        open={dialogOpen}
        handleClose={handleDialogClose}
        title="Your shortened URL"
        content={renderedDialogContent}
      />
    </div>
  );
}
