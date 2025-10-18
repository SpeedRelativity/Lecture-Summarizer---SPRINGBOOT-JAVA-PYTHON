import { useRef, useState } from "react";
import { useNavigate } from "react-router-dom";

const Home = () => {
  const fileRef = useRef<HTMLInputElement>(null);
  const [fileName, setFileName] = useState("");
  const [link, setLink] = useState("");

  const handleClick = () => {
    fileRef.current?.click();
    console.log(fileRef);
  };

  const navigate = useNavigate();

  type Job = {
    jobId: string;
    url: string;
    status: "QUEUED" | "PROCESSING" | "COMPLETED" | "FAILED";
  };

  const goToPage = async (link: string) => {
    const response = await fetch("http://localhost:8080/submission", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ url: link }),
    });
    const data: Job = await response.json();
    if (data) {
      navigate(link);
    }

    console.log("server response is: ", data);
  };

  return (
    <div className="flex">
      <div className="flex flex-col items-center justify-center w-full h-screen bg-indigo-950">
        <h1 className="font-mono text-2xl font-bold py-3 border-2 p-2">
          Upload Your File or Paste a Youtube Link
        </h1>
        <button
          id="fileButton"
          className="bg-indigo-400 text-black rounded-md px-6 py-2 my-2"
          onClick={handleClick}
        >
          Choose a File
        </button>
        <text>File Chosen: {fileName}</text>

        <input
          type="file"
          onChange={(e) => setFileName(e.target.files?.[0]?.name || "")}
          ref={fileRef}
          className="hidden"
        />
        <div className="flex flex-wrap justify-center gap-2">
          <input
            className="border-2 rounded-md px-5 py-3 m-2 text-sm"
            type="text"
            placeholder="Enter Youtube Link"
          />
          <button
            className="text-black bg-indigo-400 rounded-md px-6 py-2 my-2"
            onClick={() => goToPage("/submission")}
          >
            Submit
          </button>
        </div>
      </div>
    </div>
  );
};

export default Home;
