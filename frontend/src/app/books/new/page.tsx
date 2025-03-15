"use client";
import { useActionState } from "react";
import { addBook } from "@/app/books/actions";
import { Input } from "@/components/form/Input";

const initialState = {
  message: "",
};

export default function NewBook() {
  const [state, formAction, pending] = useActionState(addBook, initialState);

  return (
    <div className="flex flex-col min-h-screen p-8 sm:p-12">
      <div className="mb-8">
        <h1 className="text-4xl font-bold mb-4">Add New Book</h1>
        <p className="text-gray-400">Fill in the book details below</p>
      </div>

      <form action={formAction} className="max-w-2xl space-y-6">
        {state?.message && (
          <div className="p-4 mb-6 border border-red-500/20 bg-red-500/10 rounded-lg">
            <p
              className="text-red-500 dark:text-red-400 font-medium flex items-center gap-2"
              role="alert"
              aria-live="polite"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                className="h-5 w-5"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
              >
                <circle cx="12" cy="12" r="10" strokeWidth="2" />
                <path
                  d="M12 8v4m0 4h.01"
                  strokeWidth="2"
                  strokeLinecap="round"
                />
              </svg>
              {state.message}
            </p>
          </div>
        )}
        <Input label="ISBN" id="isbn" name="isbn" type="text" required />
        <Input label="Name" id="name" name="name" type="text" required />
        <Input label="Author" id="author" name="author" type="text" />
        <Input label="Pages" id="pages" name="pages" type="number" />
        <div className="flex gap-4 pt-4">
          <button
            type="submit"
            className="px-6 py-2 rounded-lg border transition-all duration-300 hover:shadow-lg hover:scale-[1.02] hover:border-slate-400 dark:hover:border-slate-600"
            disabled={pending}
          >
            Add Book
          </button>
          <a
            href="/books"
            className="px-6 py-2 rounded-lg border transition-all duration-300 hover:shadow-lg hover:scale-[1.02] hover:border-slate-400 dark:hover:border-slate-600"
          >
            Cancel
          </a>
        </div>
      </form>
    </div>
  );
}
