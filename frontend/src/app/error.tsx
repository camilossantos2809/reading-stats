"use client";

import { useEffect } from "react";
import { useRouter } from "next/navigation";

export default function Error({ error }: { error: Error }) {
  const router = useRouter();

  useEffect(() => {
    // Optionally, you can log the error to an error reporting service
    console.error("An error occurred:", error);
  }, [error]);

  return (
    <div className="flex flex-col items-center justify-center min-h-screen p-8 text-center">
      <h1 className="text-2xl font-bold">Something went wrong</h1>
      <p className="text-gray-400">{error.message}</p>
      <button
        onClick={() => router.push("/")}
        className="mt-4 px-6 py-2 rounded-lg border transition-all duration-300 hover:shadow-lg hover:scale-[1.02] hover:border-slate-400 dark:hover:border-slate-600"
      >
        Back to Home
      </button>
    </div>
  );
}
