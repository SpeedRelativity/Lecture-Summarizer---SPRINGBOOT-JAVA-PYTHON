const Processing = () => {
  return (
    <div>
      <div className="bg-red-200 h-screen w-full flex flex-col items-center ">
        <h1 className="mt-10 text-3xl">Jobs in queue: </h1>
        <table className="table-auto border-collapse border border-gray-400 mt-5">
          <thead>
            <tr>
              <th className="border border-gray-300 px-4 py-2">Job ID</th>
              <th className="border border-gray-300 px-4 py-2">Status</th>
              <th className="border border-gray-300 px-4 py-2">View Summary</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td className="border border-gray-300 px-4 py-2">12345</td>
              <td className="border border-gray-300 px-4 py-2">Processing</td>
            </tr>
            <tr>
              <td className="border border-gray-300 px-4 py-2">67890</td>
              <td className="border border-gray-300 px-4 py-2">Queued</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Processing;
