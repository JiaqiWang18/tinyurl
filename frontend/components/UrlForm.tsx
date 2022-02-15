import React from "react";
import TextField from "@mui/material/TextField";
import AccordionSummary from "@mui/material/AccordionSummary";
import useRequest from "../hooks/use-request";
import {
  Accordion,
  AccordionDetails,
  Button,
  CircularProgress,
  Tooltip,
} from "@mui/material";
import { Box } from "@mui/system";
import DialogPopup from "./DialogPopup";
import { CopyToClipboard } from "react-copy-to-clipboard";
import { DesktopDatePicker } from "@mui/lab";
import AdapterDateFns from "@mui/lab/AdapterDateFns";
import LocalizationProvider from "@mui/lab/LocalizationProvider";
import BuildIcon from "@mui/icons-material/Build";
import api from "../api";

export default function UrlForm() {
  const [originalUrl, setUrl] = React.useState("");
  const [alias, setAlias] = React.useState<string>("");
  const [expireDate, setExpireDate] = React.useState<Date | null>(null);

  const [dialogOpen, setDialogOpen] = React.useState(false);
  const [shortenedUrl, setShortenedUrl] = React.useState<string>("error");
  const [expanded, setExpanded] = React.useState(false);

  const { doRequest, errors, isFetching } = useRequest({
    url: api + "/url/generate",
    method: "post",
    body: {
      original: originalUrl,
      alias,
      expireDate,
    },
    onSuccess: (data: any) => {
      const shortenedUrl = api + "/" + data.data.hash;
      setShortenedUrl(shortenedUrl);
      setDialogOpen(true);
      saveLocalStorage(data.data.hash, originalUrl, data.data.expireDate);
    },
    onError: () => {},
  });

  const saveLocalStorage = (
    hash: string,
    originalUrl: string,
    expireDate: Date | null
  ) => {
    if (localStorage.getItem("localUrls") !== null) {
      //@ts-ignore
      const urls: Array<any> = JSON.parse(localStorage.getItem("localUrls"));
      urls.push({ hash, originalUrl, expireDate });
      localStorage.setItem("localUrls", JSON.stringify(urls));
    } else {
      const urls = [{ hash, originalUrl, expireDate }];
      localStorage.setItem("localUrls", JSON.stringify(urls));
    }
  };

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
          <Tooltip title="Copy">
            <h5>
              {shortenedUrl} <i className="fas fa-copy"></i>
            </h5>
          </Tooltip>
        </button>
      </CopyToClipboard>
    </div>
  );

  return (
    <div className="justify-content-center align-self-center mx-auto mb-5">
      <div className="form">
        <div className="mb-3">
          <h1 className="">
            TinyURL! <i className="fas fa-link"></i>
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
            <Accordion
              expanded={expanded}
              onChange={() => {
                setExpanded(!expanded);
              }}
            >
              <AccordionSummary
                aria-controls="panel1a-content"
                id="panel1a-header"
                expandIcon={
                  <Tooltip title="Customize">
                    <BuildIcon />
                  </Tooltip>
                }
              ></AccordionSummary>
              <AccordionDetails>
                <div className="form-group">
                  <TextField
                    id="outlined-basic"
                    label="Custom Alias (Optional)"
                    variant="outlined"
                    value={alias}
                    onChange={(e) => {
                      setAlias(e.target.value);
                    }}
                    fullWidth
                  />
                </div>
                <div className="form-group mt-4">
                  <LocalizationProvider dateAdapter={AdapterDateFns}>
                    <DesktopDatePicker
                      label="Expire Date (Optional)"
                      inputFormat="MM/dd/yyyy"
                      value={expireDate}
                      onChange={setExpireDate}
                      renderInput={(params) => (
                        <TextField {...params} fullWidth />
                      )}
                    />
                  </LocalizationProvider>
                </div>
              </AccordionDetails>
            </Accordion>

            <div className="form-group">
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
        title="Your tiny url"
        content={renderedDialogContent}
      />
    </div>
  );
}
