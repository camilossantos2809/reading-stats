"use client";

import { useActionState } from "react";
import { addReadingProgress } from "./actions";

const initialState = {
  message: "",
};

export function NewReadingProgressForm({ bookId }: { bookId: string }) {
  const [state, formAction, pending] = useActionState(addReadingProgress, {
    ...initialState,
    bookId,
  });

  return (
    <form
      action={formAction}
      className="max-w-2xl p-6 border rounded-lg space-y-4"
    >
      <h2 className="text-xl font-bold">Update Reading Progress</h2>
      <div className="space-y-4">
        <div>
          <label htmlFor="date" className="block text-sm font-medium">
            Date
          </label>
          <input
            type="date"
            id="date"
            className="w-full p-2 border rounded-lg [color-scheme:dark]"
            defaultValue={new Date().toISOString().split("T")[0]}
          />
        </div>
        <div>
          <label htmlFor="lastPage" className="block text-sm font-medium">
            Last Page Read
          </label>
          <input
            type="number"
            id="lastPage"
            className="w-full p-2 border rounded-lg"
            placeholder="Enter last page read"
          />
        </div>
        <div>
          <label htmlFor="percentage" className="block text-sm font-medium">
            Percentage Read
          </label>
          <input
            type="number"
            id="percentage"
            className="w-full p-2 border rounded-lg"
            placeholder="Enter percentage read"
          />
        </div>
        <button
          type="submit"
          className="px-6 py-2 rounded-lg border transition-all duration-300 hover:shadow-lg hover:scale-[1.02] hover:border-slate-400 dark:hover:border-slate-600"
          disabled={pending}
        >
          Save Progress
        </button>
      </div>
    </form>
  );
}
