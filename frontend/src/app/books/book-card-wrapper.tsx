"use client";

import { BookCard } from "@/components/book-card";
import { GoalBook } from "@/types";
import { useRouter } from "next/navigation";

export function BookCardWrapper({ book }: { book: GoalBook }) {
  const router = useRouter();

  return (
    <BookCard
      key={book.isbn}
      book={book}
      onPress={() => router.push(`/history/${book.id}`)}
    />
  );
}
