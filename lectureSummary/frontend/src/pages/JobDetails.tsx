import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";

type JobDetails = {
  jobId: string;
  url: string;
  status: "QUEUED" | "PROCESSING" | "COMPLETED" | "FAILED";
  title: string | null;
  transcription: string | null;
  summary: string | null;
  errorMessage: string | null;
  createdAt: string | null;
};

const JobDetails = () => {
  const { jobId } = useParams<{ jobId: string }>();
  const [job, setJob] = useState<JobDetails | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    fetchJobDetails();

    const interval = setInterval(() => {
      if (job?.status === "QUEUED" || job?.status === "PROCESSING") {
        fetchJobDetails();
      }
    }, 3000);

    return () => clearInterval(interval);
  }, [jobId, job?.status]);

  const fetchJobDetails = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/jobs/${jobId}/details`
      );

      if (!response.ok) {
        throw new Error("Job not found");
      }

      const data: JobDetails = await response.json();
      setJob(data);
      setLoading(false);
    } catch (err) {
      setError("Failed to load job details");
      setLoading(false);
    }
  };

  // ✅ Copy to clipboard function
  const copyToClipboard = (text: string) => {
    navigator.clipboard.writeText(text).then(
      () => {
        alert("✅ Copied to clipboard!");
      },
      (err) => {
        console.error("Failed to copy: ", err);
        alert("❌ Failed to copy to clipboard");
      }
    );
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-purple-100 to-pink-100 flex items-center justify-center">
        <div className="text-2xl font-semibold">Loading job details...</div>
      </div>
    );
  }

  if (error || !job) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-purple-100 to-pink-100 flex flex-col items-center justify-center">
        <div className="text-2xl text-red-600 mb-4">{error}</div>
        <Link
          to="/submission"
          className="bg-blue-500 text-white px-6 py-3 rounded-lg hover:bg-blue-600"
        >
          Back to Jobs
        </Link>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-100 to-pink-100 p-8">
      <div className="max-w-5xl mx-auto">
        {/* Back Button */}
        <Link
          to="/submission"
          className="text-blue-600 hover:underline mb-6 inline-block text-lg font-semibold"
        >
          ← Back to Jobs
        </Link>

        {/* Header Card */}
        <div className="bg-white rounded-xl shadow-lg p-8 mb-6">
          <h1 className="text-4xl font-bold mb-4 text-gray-800">
            {job.title || "Untitled Job"}
          </h1>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
            <div>
              <span className="font-semibold text-gray-700">Status:</span>
              <span
                className={`ml-3 px-4 py-2 rounded-full text-sm font-semibold inline-block ${
                  job.status === "COMPLETED"
                    ? "bg-green-100 text-green-800"
                    : job.status === "FAILED"
                    ? "bg-red-100 text-red-800"
                    : job.status === "PROCESSING"
                    ? "bg-yellow-100 text-yellow-800"
                    : "bg-gray-100 text-gray-800"
                }`}
              >
                {job.status}
              </span>
            </div>
            <div>
              <span className="font-semibold text-gray-700">YouTube URL:</span>

              <a
                href={job.url}
                target="_blank"
                rel="noopener noreferrer"
                className="ml-3 text-blue-600 hover:underline text-sm"
              >
                Watch Video
              </a>
            </div>
          </div>

          {job.createdAt && (
            <div className="text-sm text-gray-500">
              Created: {new Date(job.createdAt).toLocaleString()}
            </div>
          )}
        </div>

        {/* Status Messages */}
        {job.status === "QUEUED" && (
          <div className="bg-blue-50 border-l-4 border-blue-500 text-blue-800 p-6 mb-6 rounded-lg shadow">
            <p className="font-bold text-lg">⏳ Waiting in queue...</p>
            <p className="mt-2">Your job will be processed soon.</p>
          </div>
        )}

        {job.status === "PROCESSING" && (
          <div className="bg-yellow-50 border-l-4 border-yellow-500 text-yellow-800 p-6 mb-6 rounded-lg shadow">
            <p className="font-bold text-lg">🔄 Processing...</p>
            <div className="mt-3 space-y-2">
              <p className="text-sm">📥 Downloading video...</p>
              <p className="text-sm">
                🎤 Transcribing audio with Whisper AI...
              </p>
              <p className="text-sm">🤖 Generating summary with AI...</p>
            </div>
            <div className="mt-4 w-full bg-yellow-200 rounded-full h-3">
              <div
                className="bg-yellow-500 h-3 rounded-full animate-pulse"
                style={{ width: "65%" }}
              ></div>
            </div>
          </div>
        )}

        {job.status === "FAILED" && (
          <div className="bg-red-50 border-l-4 border-red-500 text-red-800 p-6 mb-6 rounded-lg shadow">
            <p className="font-bold text-lg">❌ Job Failed</p>
            <p className="mt-2">
              {job.errorMessage || "An unknown error occurred"}
            </p>
          </div>
        )}

        {/* Results - Only show when COMPLETED */}
        {job.status === "COMPLETED" && (
          <>
            {/* Summary Card */}
            <div className="bg-white rounded-xl shadow-lg p-8 mb-6">
              <div className="flex justify-between items-center mb-4">
                <h2 className="text-3xl font-bold text-gray-800">📝 Summary</h2>
                <button
                  onClick={() => copyToClipboard(job.summary || "")}
                  className="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition text-sm font-semibold"
                >
                  Copy
                </button>
              </div>
              <div className="bg-gray-50 p-6 rounded-lg border border-gray-200">
                <p className="text-gray-800 whitespace-pre-wrap leading-relaxed text-lg">
                  {job.summary || "No summary available"}
                </p>
              </div>
            </div>

            {/* Transcription Card */}
            <div className="bg-white rounded-xl shadow-lg p-8">
              <div className="flex justify-between items-center mb-4">
                <h2 className="text-3xl font-bold text-gray-800">
                  Full Transcription
                </h2>
                <button
                  onClick={() => copyToClipboard(job.transcription || "")}
                  className="bg-green-500 text-white px-4 py-2 rounded-lg hover:bg-green-600 transition text-sm font-semibold"
                >
                  Copy
                </button>
              </div>
              <div className="bg-gray-50 p-6 rounded-lg max-h-96 overflow-y-auto border border-gray-200">
                <p className="text-gray-700 whitespace-pre-wrap leading-relaxed">
                  {job.transcription || "No transcription available"}
                </p>
              </div>
              <div className="mt-4 text-sm text-gray-500">
                {job.transcription?.length.toLocaleString()} characters
              </div>
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default JobDetails;
