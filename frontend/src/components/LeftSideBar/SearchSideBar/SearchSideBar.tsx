import React, { useEffect, useState } from "react";
import { apiClient } from "../../../services/api";
import "./SearchSideBar.css";
import { Combatant } from "../../type/Combatant";
import { useSearch } from "../../../hooks/useSearch";
import { useDebounce } from "../../../hooks/useDebounce";
import { useAddCombatant } from "../../../hooks/useAddCombatant";
import { ReactComponent as PlusIcon } from "../../../icons/plus.svg";
import { ReactComponent as DotIcon } from "../../../icons/dot.svg"
import { Combat } from "../../type/Combat";

type SearchSideBarProps = {
  combat: Combat | undefined,
  setCombat: React.Dispatch<React.SetStateAction<Combat | undefined>>
}

export const SearchSideBar = ({ combat, setCombat } : SearchSideBarProps) => {
  
  useEffect(() => {
    console.log(combat);

  }, [combat]);

  const [isSearchingCreatures, setIsSearchingCreatures] = useState(true);
  const [results, setResults] = useState<Combatant[]>([]);
  const [query, setQuery] = useState("");
  const [pagination, setPagination] = useState({
    size: 10,
    number: 0,
    totalElements: 0,
    totalPages: 0,
  });

  const debouncedQuery = useDebounce(query, 300);

  const {
    handleSearch,
    isSearching
  } = useSearch(isSearchingCreatures, setResults, pagination, setPagination);

  const {
    handleAddCombatant,
    isAdding
  } = useAddCombatant({combat, setCombat});

  useEffect(() => {
    if (debouncedQuery.trim()) {
      handleSearch(debouncedQuery, 0);
    }
  }, [debouncedQuery])

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const query = e.target.value;
    setQuery(query);
  };

  const handlePageChange = (newPage: number) => {
    if (newPage >= 0 && newPage < pagination.totalPages) {
      handleSearch(query, newPage);
    }
  };

  const getPageNumbers = () => {
    const maxVisible = 5;
    const half = Math.floor(maxVisible/2);
    let start = Math.max(0, pagination.number-half);
    let end = Math.min(pagination.totalPages - 1, pagination.number + half);

    if (end - start + 1 < maxVisible) {
      if (start === 0) {
        end = Math.min(pagination.totalPages - 1, start + maxVisible - 1);
      } else if (end === pagination.totalPages - 1) {
        start = Math.max(0, end - maxVisible + 1);
      }
    }

    const pages = [];
    for (let i = start; i <= end; i++) {
      pages.push(i);
    }
    return pages;
  }

  return (
    <aside className="search-sidebar-container">
      <div className="search-sidebar__input-container">
        <input
          type="text"
          value={query}
          onChange={handleInputChange}
          placeholder="Type to search..."
          className="search-input"
        />
      </div>
      <div className="search-sidebar__filter-container">
        <button 
          className="search-sidebar__filter-button"
          onClick={() => setIsSearchingCreatures(!isSearchingCreatures)}
          >
            {isSearchingCreatures ? "Searching for monsters" : "Searching for characters"}
        </button>
      </div>
      <ul className="search-results">
        {results.map((creature, idx) => (
          <li key={idx} className="search-result-item">
            <DotIcon className="search-result-item-dot"/>
            <span className="search-result-item-name">{creature.name}</span>
            <button
              className="add-combatant-button"
              onClick={() => handleAddCombatant(creature)}
            >
              
              <PlusIcon className="add-combatant-button-icon" />
            </button>
          </li>
        ))}
        {query && results.length === 0 && (
          <li className="no-results">No creatures found.</li>
        )}
      </ul>
      {results && results.length > 0 ? (
        <div className="pagination">
          <button
            onClick={() => handlePageChange(pagination.number - 1)}
            disabled={pagination.number === 0}
          >
            {"< Back"}
          </button>

          <span>
            {getPageNumbers().map(pageNumber => (
              <span
                key={pageNumber}
                className={`page-number ${pageNumber === pagination.number ? "active" : ""}`}
                onClick={() => handlePageChange(pageNumber)}
                >
                  {pageNumber + 1}
              </span>
            ))}
          </span>

          <button
            onClick={() => handlePageChange(pagination.number + 1)}
            disabled={pagination.number >= pagination.totalPages - 1}
          >
            {"Next >"}
          </button>
        </div>
      ) : null} 
    </aside>
  );
}
export {};
