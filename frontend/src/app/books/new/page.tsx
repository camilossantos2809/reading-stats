"use client";
import { useActionState } from "react";
import { addBook } from "@/app/books/actions";
import { Input } from "@/components/form/input";
import Link from "next/link";
import { FormStatus, FormStatusProps } from "@/components/form/FormStatus";

const initialState: FormStatusProps = {
  message: "",
  status: "none",
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
        <FormStatus message={state?.message} status={state?.status ?? "none"} />
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
          <Link
            href="/books"
            className="px-6 py-2 rounded-lg border transition-all duration-300 hover:shadow-lg hover:scale-[1.02] hover:border-slate-400 dark:hover:border-slate-600"
          >
            Cancel
          </Link>
        </div>
      </form>
    </div>
  );
}
