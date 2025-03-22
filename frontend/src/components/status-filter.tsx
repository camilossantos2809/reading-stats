"use client";

import { useCallback, useTransition } from "react";
import { useRouter, useSearchParams } from "next/navigation";

type BookStatus = "reading" | "to_read" | "completed";

interface StatusFilterProps {
  selectedStatus: string | string[] | null;
}

const statusConfig: Record<BookStatus, { label: string; colors: string }> = {
  reading: {
    label: "Reading",
    colors: "bg-blue-500/10 text-blue-500 border-blue-500/20",
  },
  to_read: {
    label: "To Read",
    colors: "bg-yellow-500/10 text-yellow-500 border-yellow-500/20",
  },
  completed: {
    label: "Completed",
    colors: "bg-green-500/10 text-green-500 border-green-500/20",
  },
};

export function StatusFilter({ selectedStatus }: StatusFilterProps) {
  const router = useRouter();
  const searchParams = useSearchParams();
  const [isPending, startTransition] = useTransition();

  const handleClick = useCallback(
    (status: BookStatus) => {
      const params = new URLSearchParams(searchParams);

      if (selectedStatus === status) {
        params.delete("status");
      } else {
        params.set("status", status);
      }

      startTransition(() => {
        router.push(`?${params.toString()}`);
      });
    },
    [selectedStatus, router, searchParams]
  );

  return (
    <div className="flex gap-2 flex-wrap">
      {(
        Object.entries(statusConfig) as [
          BookStatus,
          typeof statusConfig.reading
        ][]
      ).map(([status, config]) => (
        <button
          key={status}
          onClick={() => handleClick(status)}
          disabled={isPending}
          className={`
              px-4 py-2 rounded-full border transition-all text-sm
              ${isPending ? "opacity-50" : ""}
              ${
                selectedStatus === status
                  ? `${config.colors} border-current`
                  : "border-gray-200 dark:border-gray-700 hover:border-current"
              }
              ${
                selectedStatus === status
                  ? ""
                  : config.colors
                      .replace("bg-", "hover:bg-")
                      .replace("border-", "hover:border-")
              }
            `}
        >
          {config.label}
        </button>
      ))}
    </div>
  );
}
