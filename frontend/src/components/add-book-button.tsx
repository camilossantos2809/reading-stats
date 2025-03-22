"use client";

import { useState } from "react";
import { Book } from "@/types";
import { AddBookModal } from "./add-book-modal";

export function AddBookButton() {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleBookSelect = (book: Book) => {
    console.log("Selected book:", book);
    // This will be replaced with API call later
  };

  return (
    <>
      <button
        onClick={() => setIsModalOpen(true)}
        className="px-6 py-2 rounded-lg border transition-all duration-300 hover:shadow-lg hover:scale-[1.02] hover:border-slate-400 dark:hover:border-slate-600"
      >
        Add Book
      </button>

      <AddBookModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onSelect={handleBookSelect}
      />
    </>
  );
}
