import { useEffect, useState } from "react";

type Job = {
  jobId: string;
  url: string;
  status: "QUEUED" | "PROCESSING" | "COMPLETED" | "FAILED";
};
const Processing = () => {
  const [jobs, setJobs] = useState<Job[]>([]);
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    fetchJobs();
  }, []);

  const fetchJobs = async () => {
    try {
      const response = await fetch("http://localhost:8080/jobs");
      const data = await response.json();
      setJobs(data);
      setLoading(false);
    } catch (error) {
      console.error("Error fetching jobs:", error);
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="bg-red-200 h-screen w-full flex items-center justify-center">
        <h1 className="text-2xl">Loading jobs...</h1>
      </div>
    );
  } else {
    return (
      <div>
        <div className="bg-red-200 h-screen w-full flex flex-col items-center">
          <h1 className="mt-10 text-3xl">Jobs in queue: {jobs.length}</h1>
          <table className="table-auto border-collapse border border-gray-400 mt-5">
            <thead>
              <tr>
                <th className="border border-gray-300 px-4 py-2">Job ID</th>
                <th className="border border-gray-300 px-4 py-2">URL</th>
                <th className="border border-gray-300 px-4 py-2">Status</th>
                <th className="border border-gray-300 px-4 py-2">
                  View Summary
                </th>
              </tr>
            </thead>
            <tbody>
              {jobs.map((job) => (
                <tr key={job.jobId}>
                  <td className="border border-gray-300 px-4 py-2">
                    {job.jobId.substring(0, 8)}...
                  </td>
                  <td className="border border-gray-300 px-4 py-2">
                    {job.url}
                  </td>
                  <td className="border border-gray-300 px-4 py-2">
                    {job.status}
                  </td>
                  <td className="border border-gray-300 px-4 py-2">
                    <button className="bg-blue-500 text-white px-3 py-1 rounded">
                      View
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>
    );
  }
};

export default Processing;
