import { useRef, useState } from "react";

const Home = () => {
  const fileRef = useRef<HTMLInputElement>(null);
  const [fileName, setFileName] = useState("");

  const handleClick = () => {
    fileRef.current?.click();
    console.log(fileRef);
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
          <button className="text-black bg-indigo-400 rounded-md px-6 py-2 my-2">
            Upload
          </button>
        </div>
      </div>
    </div>
  );
};

export default Home;
