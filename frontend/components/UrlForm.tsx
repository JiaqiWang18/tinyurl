import * as React from "react";
import TextField from "@mui/material/TextField";
import useRequest from "../hooks/use-request";
import { Button } from "@mui/material";
import { Box } from "@mui/system";
import DialogPopup from "./DialogPopup";

export default function UrlForm() {
  const [originalUrl, setUrl] = React.useState("");
  const [alias, setAlias] = React.useState<string>("");

  const [dialogOpen, setDialogOpen] = React.useState(false);
  const [shortenedUrl, setShortenedUrl] = React.useState<string | null>(null);

  const { doRequest, errors } = useRequest({
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

  const generateShortUrl = () => {
    doRequest();
  };

  const handleDialogClose = () => {
    setDialogOpen(false);
  };

  return (
    <div className="justify-content-center align-self-center mx-auto">
      <div className="form">
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
      </div>
      <DialogPopup
        open={dialogOpen}
        handleClose={handleDialogClose}
        title="Your shortened URL"
        content={shortenedUrl}
      />
    </div>
  );
}
