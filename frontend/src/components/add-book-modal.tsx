"use client";

import { useCallback, useState } from "react";
import { Book } from "@/types";
import Image from "next/image";

interface AddBookModalProps {
  isOpen: boolean;
  onClose: () => void;
  onSelect: (book: Book) => void;
}

// Mock available books for now
const availableBooks: Book[] = [
  {
    id: 1,
    isbn: "9781492052203",
    name: "Architecture Patterns with Python",
    author: "Harry Percival",
    pages: 290,
  },
  {
    id: 2,
    isbn: "9780451524935",
    name: "1984",
    author: "George Orwell",
    pages: 328,
  },
  {
    id: 3,
    isbn: "9780061120084",
    name: "To Kill a Mockingbird",
    author: "Harper Lee",
    pages: 324,
  },
  {
    id: 4,
    isbn: "9780141439518",
    name: "Pride and Prejudice",
    author: "Jane Austen",
    pages: 432,
  },
  {
    id: 5,
    isbn: "9781617295850",
    name: "Microservices Patterns",
    author: "Chris Richardson",
    pages: 520,
  },
  {
    id: 6,
    isbn: "9780743273565",
    name: "The Great Gatsby",
    author: "F. Scott Fitzgerald",
    pages: 180,
  },
  {
    id: 7,
    isbn: "9781617294136",
    name: "Grokking Algorithms",
    author: "Aditya Bhargava",
    pages: 256,
  },
  {
    id: 8,
    isbn: "9780261103573",
    name: "The Lord of the Rings",
    author: "J.R.R. Tolkien",
    pages: 1178,
  },
  {
    id: 9,
    isbn: "9780134685991",
    name: "Clean Architecture",
    author: "Robert C. Martin",
    pages: 432,
  },
  {
    id: 10,
    isbn: "9780062315007",
    name: "The Alchemist",
    author: "Paulo Coelho",
    pages: 208,
  },
  {
    id: 11,
    isbn: "9780547928227",
    name: "The Hobbit",
    author: "J.R.R. Tolkien",
    pages: 366,
  },
  {
    id: 12,
    isbn: "9780307474278",
    name: "Don Quixote",
    author: "Miguel de Cervantes",
    pages: 992,
  },
  {
    id: 13,
    isbn: "9780735619678",
    name: "Code Complete",
    author: "Steve McConnell",
    pages: 960,
  },
  {
    id: 14,
    isbn: "9780596007126",
    name: "Head First Design Patterns",
    author: "Eric Freeman",
    pages: 694,
  },
  {
    id: 15,
    isbn: "9780201633610",
    name: "Design Patterns",
    author: "Erich Gamma",
    pages: 395,
  },
];

export function AddBookModal({ isOpen, onClose, onSelect }: AddBookModalProps) {
  const [search, setSearch] = useState("");
  const [results, setResults] = useState(availableBooks);

  const handleSearch = useCallback((value: string) => {
    setSearch(value);
    // Mock search functionality - will be replaced with API call
    const filtered = availableBooks.filter(
      (book) =>
        book.name.toLowerCase().includes(value.toLowerCase()) ||
        book.author?.toLowerCase().includes(value.toLowerCase())
    );
    setResults(filtered);
  }, []);

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50">
      <div className="bg-background w-full max-w-2xl rounded-lg shadow-lg p-6 space-y-4 max-h-[80vh] flex flex-col">
        <div className="flex justify-between items-center">
          <h2 className="text-xl font-bold">Add Book to Goal</h2>
          <button
            onClick={onClose}
            className="text-gray-400 hover:text-gray-600 dark:hover:text-gray-200 transition-colors"
          >
            <svg
              className="w-6 h-6"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M6 18L18 6M6 6l12 12"
              />
            </svg>
          </button>
        </div>

        <div className="relative">
          <input
            type="text"
            placeholder="Search books by title or author..."
            value={search}
            onChange={(e) => handleSearch(e.target.value)}
            className="w-full p-2 pr-8 rounded-lg border bg-transparent transition-all duration-300 focus:outline-none focus:border-slate-400 dark:focus:border-slate-600"
          />
          <svg
            className="w-5 h-5 absolute right-2 top-2.5 text-gray-400"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth={2}
              d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
            />
          </svg>
        </div>

        <div className="flex-1 overflow-auto">
          {results.length > 0 ? (
            <div className="space-y-2">
              {results.map((book) => (
                <button
                  key={book.id}
                  onClick={() => {
                    onSelect(book);
                    onClose();
                  }}
                  className="w-full flex gap-4 p-3 border rounded-lg transition-all duration-300 hover:shadow-lg hover:border-slate-400 dark:hover:border-slate-600 text-left"
                >
                  <div className="relative w-[50px] h-[75px] rounded-lg overflow-hidden flex-shrink-0">
                    <Image
                      alt={`${book.name} cover`}
                      src={`https://covers.openlibrary.org/b/isbn/${book.isbn}-M.jpg`}
                      fill
                      className="object-cover"
                    />
                  </div>
                  <div>
                    <h3 className="font-bold text-sm">{book.name}</h3>
                    <p className="text-xs text-gray-400">{book.author}</p>
                    <p className="text-xs text-gray-400">{book.pages} pages</p>
                  </div>
                </button>
              ))}
            </div>
          ) : (
            <div className="text-center text-gray-400 py-8">
              No books found matching your search
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
