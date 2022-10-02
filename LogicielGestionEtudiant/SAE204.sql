-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:8889
-- Généré le : dim. 02 oct. 2022 à 10:33
-- Version du serveur :  5.7.34
-- Version de PHP : 7.4.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `SAE204`
--

-- --------------------------------------------------------

--
-- Structure de la table `ABSENCE`
--

CREATE TABLE `ABSENCE` (
  `id_abs` int(11) NOT NULL,
  `date_abs` date NOT NULL,
  `cours_abs` varchar(50) NOT NULL,
  `heure_abs` time NOT NULL,
  `id_pers_harp` int(11) NOT NULL,
  `id_just` int(11) DEFAULT NULL,
  `id_etu` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `ABSENCE`
--

INSERT INTO `ABSENCE` (`id_abs`, `date_abs`, `cours_abs`, `heure_abs`, `id_pers_harp`, `id_just`, `id_etu`) VALUES
(0, '2022-02-21', 'Dev. Interfaces Web', '14:30:00', 25727, 1, 4),
(1, '2022-01-03', 'Exploitation de bases de données', '09:00:00', 58753, 1, 5),
(3, '2022-03-13', 'Commande de bas niveaux', '13:30:00', 37373, 2, 5),
(6, '2022-03-03', 'Exploitation de bases de données', '12:00:00', 58753, 3, 1);

-- --------------------------------------------------------

--
-- Structure de la table `ETUDIANT`
--

CREATE TABLE `ETUDIANT` (
  `id_etu` int(11) NOT NULL,
  `num_etu` int(11) NOT NULL,
  `nom_etu` varchar(50) NOT NULL,
  `prenom_etu` varchar(50) NOT NULL,
  `age_etu` smallint(6) NOT NULL,
  `dateN_etu` varchar(50) DEFAULT NULL,
  `statut_etu` varchar(50) NOT NULL,
  `desc_am_etu` varchar(100) DEFAULT NULL,
  `url_photo_etu` varchar(50) DEFAULT NULL,
  `promo_etu` varchar(10) NOT NULL,
  `id_groupe` int(11) DEFAULT NULL,
  `mdp_etu` varchar(300) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `ETUDIANT`
--

INSERT INTO `ETUDIANT` (`id_etu`, `num_etu`, `nom_etu`, `prenom_etu`, `age_etu`, `dateN_etu`, `statut_etu`, `desc_am_etu`, `url_photo_etu`, `promo_etu`, `id_groupe`, `mdp_etu`) VALUES
(0, 2768, 'BAUMARD', 'Jordan', 19, '2003-02-17', 'RAS', 'null', 'avatar1.jpg', 'BUT1', 0, 'vHVYvB3bHIxn4qDEELy6m8CKYsNlwdnmQ7lzkhS9pB3jWZpdVryBbVQC1DYPNU7y5XJy/qqsu0z3bwvF9p9/xA=='),
(1, 16875, 'BETIN', 'Ewen', 18, '2003-04-26', 'RAS', 'null', 'avatar2.jpg', 'BUT1', 0, 'vHVYvB3bHIxn4qDEELy6m8CKYsNlwdnmQ7lzkhS9pB3jWZpdVryBbVQC1DYPNU7y5XJy/qqsu0z3bwvF9p9/xA=='),
(2, 25783, 'ROPERT', 'Nathan', 18, '2003-05-04', 'RAS', 'Tier temps', 'avatar3.jpg', 'BUT1', 3, 'vHVYvB3bHIxn4qDEELy6m8CKYsNlwdnmQ7lzkhS9pB3jWZpdVryBbVQC1DYPNU7y5XJy/qqsu0z3bwvF9p9/xA=='),
(3, 33837, 'WALQUAN', 'Juliette', 18, '2003-12-08', 'RAS', 'null', 'avatar4.jpg', 'BUT1', 3, 'vHVYvB3bHIxn4qDEELy6m8CKYsNlwdnmQ7lzkhS9pB3jWZpdVryBbVQC1DYPNU7y5XJy/qqsu0z3bwvF9p9/xA=='),
(4, 26374, 'HAUTMANN', 'Kevin', 18, '2003-05-25', 'À surveiller', 'null', 'avatar5.jpg', 'BUT1', 0, '4TvCusBlQEmbCjiNSa7r35FvBycXD/90hKiW/ny0WE8S0DFaQ1/V+TiL26ehff2PlyvqkQK8QzXxD5jXwHYFQA=='),
(5, 55964, 'DIRIBARNE', 'Vincent', 19, '2003-08-24', 'Défaillant', 'null', 'avatar6.jpg', 'BUT1', 1, 'vHVYvB3bHIxn4qDEELy6m8CKYsNlwdnmQ7lzkhS9pB3jWZpdVryBbVQC1DYPNU7y5XJy/qqsu0z3bwvF9p9/xA=='),
(6, 9876, 'Tobi', 'Modepasso', 17, '2004-06-23', 'RAS', '', '', 'BUT1', 0, 'vHVYvB3bHIxn4qDEELy6m8CKYsNlwdnmQ7lzkhS9pB3jWZpdVryBbVQC1DYPNU7y5XJy/qqsu0z3bwvF9p9/xA=='),
(7, 54352, 'GUILLIOT', 'Cyrielle', 19, '2002-06-21', 'À vérifier', '', '', 'BUT1', 2, 'vHVYvB3bHIxn4qDEELy6m8CKYsNlwdnmQ7lzkhS9pB3jWZpdVryBbVQC1DYPNU7y5XJy/qqsu0z3bwvF9p9/xA==');

-- --------------------------------------------------------

--
-- Structure de la table `GROUPE`
--

CREATE TABLE `GROUPE` (
  `id_groupe` int(11) NOT NULL,
  `type_groupe` varchar(50) NOT NULL,
  `id_td_groupe` int(11) NOT NULL,
  `tp_groupe` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `GROUPE`
--

INSERT INTO `GROUPE` (`id_groupe`, `type_groupe`, `id_td_groupe`, `tp_groupe`) VALUES
(0, 'TD', 11, 'TP11A'),
(1, 'TD', 11, 'TP11B'),
(2, 'TD', 12, 'TP12A'),
(3, 'TD', 12, 'TP12B');

-- --------------------------------------------------------

--
-- Structure de la table `JUSTIFICATIF`
--

CREATE TABLE `JUSTIFICATIF` (
  `id_just` int(11) NOT NULL,
  `dateD_just` datetime NOT NULL,
  `dateF_just` datetime NOT NULL,
  `url_fichier_just` varchar(50) DEFAULT NULL,
  `etat_just` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `JUSTIFICATIF`
--

INSERT INTO `JUSTIFICATIF` (`id_just`, `dateD_just`, `dateF_just`, `url_fichier_just`, `etat_just`) VALUES
(0, '2022-02-21 00:00:00', '2022-02-21 00:00:00', 'C:justificatifshautmannkjjustif12.pdf', 'En attente'),
(1, '2022-03-15 00:00:00', '2022-03-17 00:00:00', 'C:justificatifsdiribarnevjustif1.pdf', 'Non validé'),
(2, '2022-03-16 00:00:00', '2022-03-16 00:00:00', 'C:justificatifswalquanjjustif2.pdf', 'Validé'),
(3, '2022-04-06 00:00:00', '2022-04-06 00:00:00', 'C:justificatifsdiribarnevjustif3.pdf', 'Validé'),
(4, '2022-05-15 00:00:00', '2022-05-15 00:00:00', 'C:justificatifs\ropertnjustif1.pdf', 'Non validé'),
(5, '2022-05-19 00:00:00', '2022-05-21 00:00:00', 'C:justificatifsaumardjjustif1.pdf', 'Non validé');

-- --------------------------------------------------------

--
-- Structure de la table `PERSONNEL`
--

CREATE TABLE `PERSONNEL` (
  `id_pers_harp` int(11) NOT NULL,
  `nom_pers` varchar(50) NOT NULL,
  `prenom_pers` varchar(50) NOT NULL,
  `id_role` int(11) NOT NULL,
  `mdp_pers` varchar(300) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `PERSONNEL`
--

INSERT INTO `PERSONNEL` (`id_pers_harp`, `nom_pers`, `prenom_pers`, `id_role`, `mdp_pers`) VALUES
(25727, 'DESBOURDES', 'Elisabeth', 1, 'vHVYvB3bHIxn4qDEELy6m8CKYsNlwdnmQ7lzkhS9pB3jWZpdVryBbVQC1DYPNU7y5XJy/qqsu0z3bwvF9p9/xA=='),
(37373, 'LAFORCADE', 'Pierre', 2, 'vHVYvB3bHIxn4qDEELy6m8CKYsNlwdnmQ7lzkhS9pB3jWZpdVryBbVQC1DYPNU7y5XJy/qqsu0z3bwvF9p9/xA=='),
(58753, 'HAMON', 'Ludovic', 2, 'vHVYvB3bHIxn4qDEELy6m8CKYsNlwdnmQ7lzkhS9pB3jWZpdVryBbVQC1DYPNU7y5XJy/qqsu0z3bwvF9p9/xA==');

-- --------------------------------------------------------

--
-- Structure de la table `ROLE`
--

CREATE TABLE `ROLE` (
  `id_role` int(11) NOT NULL,
  `nom_role` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `ROLE`
--

INSERT INTO `ROLE` (`id_role`, `nom_role`) VALUES
(0, 'Étudiant'),
(1, 'Secrétaire'),
(2, 'Enseignant'),
(3, 'Directeur des études');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `ABSENCE`
--
ALTER TABLE `ABSENCE`
  ADD PRIMARY KEY (`id_abs`),
  ADD KEY `id_pers_harp` (`id_pers_harp`),
  ADD KEY `id_just` (`id_just`),
  ADD KEY `id_etu` (`id_etu`);

--
-- Index pour la table `ETUDIANT`
--
ALTER TABLE `ETUDIANT`
  ADD PRIMARY KEY (`id_etu`),
  ADD UNIQUE KEY `num_etu` (`num_etu`),
  ADD KEY `id_groupe` (`id_groupe`);

--
-- Index pour la table `GROUPE`
--
ALTER TABLE `GROUPE`
  ADD PRIMARY KEY (`id_groupe`);

--
-- Index pour la table `JUSTIFICATIF`
--
ALTER TABLE `JUSTIFICATIF`
  ADD PRIMARY KEY (`id_just`);

--
-- Index pour la table `PERSONNEL`
--
ALTER TABLE `PERSONNEL`
  ADD PRIMARY KEY (`id_pers_harp`),
  ADD KEY `id_role` (`id_role`);

--
-- Index pour la table `ROLE`
--
ALTER TABLE `ROLE`
  ADD PRIMARY KEY (`id_role`);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `ABSENCE`
--
ALTER TABLE `ABSENCE`
  ADD CONSTRAINT `absence_ibfk_1` FOREIGN KEY (`id_pers_harp`) REFERENCES `PERSONNEL` (`id_pers_harp`),
  ADD CONSTRAINT `absence_ibfk_2` FOREIGN KEY (`id_just`) REFERENCES `JUSTIFICATIF` (`id_just`),
  ADD CONSTRAINT `absence_ibfk_3` FOREIGN KEY (`id_etu`) REFERENCES `ETUDIANT` (`id_etu`);

--
-- Contraintes pour la table `ETUDIANT`
--
ALTER TABLE `ETUDIANT`
  ADD CONSTRAINT `etudiant_ibfk_1` FOREIGN KEY (`id_groupe`) REFERENCES `GROUPE` (`id_groupe`);

--
-- Contraintes pour la table `PERSONNEL`
--
ALTER TABLE `PERSONNEL`
  ADD CONSTRAINT `personnel_ibfk_1` FOREIGN KEY (`id_role`) REFERENCES `ROLE` (`id_role`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
