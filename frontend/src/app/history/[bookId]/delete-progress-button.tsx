"use client";

import { useTransition } from "react";
import { deleteReadingProgress } from "./actions";

interface DeleteProgressButtonProps {
  progressId: string;
}

export function DeleteProgressButton({
  progressId,
}: DeleteProgressButtonProps) {
  const [isPending, startTransition] = useTransition();

  function handleDelete() {
    if (progressId == null) return;

    startTransition(async () => {
      const result = await deleteReadingProgress(progressId);
      if (result.status === "error") {
        // You might want to add proper error handling here
        console.error(result.message);
      }
    });
  }

  return (
    <button
      onClick={handleDelete}
      disabled={isPending}
      className="px-3 py-1 text-sm text-red-500 hover:text-red-700 hover:bg-red-100/10 rounded-md transition-colors"
    >
      {isPending ? "Deleting..." : "Delete"}
    </button>
  );
}
