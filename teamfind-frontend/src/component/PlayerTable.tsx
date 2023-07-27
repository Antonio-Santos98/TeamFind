import React, { useEffect, useState } from "react";
import axios from "axios";

interface Player {
  userId: number;
  userName: string;
  preferredRole: string;
  rating: number;
  playerRank: string;
  teamId: number | null;
}

const PlayerTable: React.FC = () => {
  const [players, setPlayers] = useState<Player[]>([]);

  useEffect(() => {
    const fetchPlayers = async () => {
      try {
        const response = await axios.get("http://localhost:8181/api/player");
        setPlayers(response.data);
      } catch (error) {
        console.error("Error fetching players:", error);
      }
    };

    fetchPlayers();
  }, []);

  return (
    <div>
      <h1>Player List</h1>
      <table>
        <thead>
          <tr>
            <th>User ID</th>
            <th>User Name</th>
            <th>Preferred Role</th>
            <th>Rating</th>
            <th>Player Rank</th>
            <th>Team ID</th>
          </tr>
        </thead>
        <tbody>
          {players.map((player) => (
            <tr key={player.userId}>
              <td>{player.userId}</td>
              <td>{player.userName}</td>
              <td>{player.preferredRole}</td>
              <td>{player.rating}</td>
              <td>{player.playerRank}</td>
              <td>{player.teamId || "N/A"}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default PlayerTable;
