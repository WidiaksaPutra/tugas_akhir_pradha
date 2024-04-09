-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 30 Bulan Mei 2020 pada 12.39
-- Versi server: 10.4.10-MariaDB
-- Versi PHP: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pradha`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `penyedia_dan_wisata`
--

CREATE TABLE `penyedia_dan_wisata` (
  `keypradha1` int(11) NOT NULL,
  `Nama_Lengkap` varchar(60) DEFAULT NULL,
  `Negara` varchar(20) DEFAULT NULL,
  `Alamat` varchar(100) DEFAULT NULL,
  `Username` varchar(60) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `Gambar` varchar(255) DEFAULT NULL,
  `Umur` varchar(3) DEFAULT NULL,
  `Jenis_Kelamin` enum('Famale','Male') DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `HP` varchar(20) DEFAULT NULL,
  `User` enum('Traveler','Travel_Provider') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `penyedia_dan_wisata`
--

INSERT INTO `penyedia_dan_wisata` (`keypradha1`, `Nama_Lengkap`, `Negara`, `Alamat`, `Username`, `Password`, `Gambar`, `Umur`, `Jenis_Kelamin`, `Email`, `HP`, `User`) VALUES
(104, 'I Gusti Ngurah Agung Widiaksa Putra', 'Indonesia', 'Pesraman UNUD Blok D/9', 'agungwidiaksa', '$2y$10$sAGKEjC1tDtHi8HpUWGeXukuPB..fEZiQYh.JGJaAKsYqPtcxFgfq', NULL, '20', 'Male', 'widiaksajaya.WIP@gmail.com', '085333295221', 'Traveler'),
(105, 'I Gusti Ngurah Agung Widiaksa Putra', 'Indonesia', 'Pesraman UNUD Blok D/9', 'agungwijaya', '$2y$10$lqTzOdo5nv7ognuMEq1uquLyc6YAkKekQWKH73GBOuVWMGLA2Jibe', NULL, '20', 'Male', 'widiaksaja.wip@gmail.com', '085333295221', 'Travel_Provider'),
(106, 'wira', 'assadsa', 'adasd', 'wira', '$2y$10$WcWi6J/k49Qzmq.Z2vKgdOwKDCFaxXRrpuK2CwoaO3dtPoAu3Iumu', NULL, '20', 'Male', 'asdadda', '21312421421', 'Travel_Provider'),
(108, 'agungsuputra', 'jepang', 'gdhajgdhadd', 'suputra', '$2y$10$AzBgxM4nVsmlIgmjfAd5JeDHvc9TYyzpfj.eAnkgsa0MTklklyT2y', NULL, '20', 'Famale', 'suputra@gmail.com', '273672632', 'Traveler'),
(109, 'sarasvati', 'india', 'dasdsad', 'sarasvati', '$2y$10$P1Ehq43MhC0iL2HR8xVEtuGTaVRiLH1i9.lOfaCf0Oz06MR/adtZa', NULL, '25', 'Famale', 'sndaksjndjskadj@gmail.com', '2312321', 'Traveler'),
(110, 'agung', 'indonesia', 'shadfgas', 'agung', '$2y$10$Xi1mjrZZkXiZwgOlu1ph.OGfLQwcR5DEYx3i.g6g1.fDm3pmfKk9u', NULL, '20', 'Male', 'shgdhasd@jhgjhgfs', '623726321', 'Travel_Provider');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tempat_wisata`
--

CREATE TABLE `tempat_wisata` (
  `keypradha2` int(11) NOT NULL,
  `Nama_tempat` varchar(50) NOT NULL,
  `Deskripsi` text NOT NULL,
  `Waktu` varchar(60) NOT NULL,
  `Hari` varchar(10) NOT NULL,
  `Alamat_Lokasi` varchar(255) NOT NULL,
  `GPS` varchar(255) NOT NULL,
  `Harga` int(11) NOT NULL,
  `Persyaratan` text NOT NULL,
  `Gambar` varchar(255) NOT NULL,
  `Jenis_Tempat` enum('Umum_Bali','Budaya_Adat') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tempat_wisata`
--

INSERT INTO `tempat_wisata` (`keypradha2`, `Nama_tempat`, `Deskripsi`, `Waktu`, `Hari`, `Alamat_Lokasi`, `GPS`, `Harga`, `Persyaratan`, `Gambar`, `Jenis_Tempat`) VALUES
(91, 'Pura Danu Beratan', 'bagus', 'everytime', '', 'Bangli', '', 10000, 'harus memakai kamen dan selendang', 'http://192.168.1.104/web/pradha/upload/img4229.jpg', 'Umum_Bali'),
(92, 'Pantai Nusa Dua', 'Pantai dengan terdapat pulau yang bagus', 'everytime', '', 'Jimbaran', '', 5000, '', 'http://192.168.1.104/web/pradha/upload/img5049.jpg', 'Umum_Bali'),
(95, 'Upacara Adat Dewa Yadnya di Petang', 'Upacara keagamaan di pura dalem', 'senin 07,00 - rabu 11,00', '', 'Petang', '', 0, 'Harus menggunakan pakaian Sopan, selendang dan kamen.', 'http://192.168.1.104/web/pradha/upload/img4903.jpg', 'Budaya_Adat'),
(96, 'agung batur', 'sakjgdjhdjsad', '3123213d', '', '', '', 13212, 'dsadadas', 'http://192.168.1.104/web/pradha/upload/img8065.jpg', 'Umum_Bali');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi`
--

CREATE TABLE `transaksi` (
  `id_transaksi` int(11) NOT NULL,
  `keypradha1` int(11) NOT NULL,
  `keypradha2` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `transaksi`
--

INSERT INTO `transaksi` (`id_transaksi`, `keypradha1`, `keypradha2`) VALUES
(146, 105, 91),
(147, 105, 92),
(151, 105, 95),
(152, 105, 96),
(156, 109, 91),
(158, 109, 95),
(159, 104, 91),
(160, 104, 95);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `penyedia_dan_wisata`
--
ALTER TABLE `penyedia_dan_wisata`
  ADD PRIMARY KEY (`keypradha1`);

--
-- Indeks untuk tabel `tempat_wisata`
--
ALTER TABLE `tempat_wisata`
  ADD PRIMARY KEY (`keypradha2`);

--
-- Indeks untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id_transaksi`),
  ADD KEY `keypradha1` (`keypradha1`) USING BTREE,
  ADD KEY `keypradha2` (`keypradha2`) USING BTREE;

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `penyedia_dan_wisata`
--
ALTER TABLE `penyedia_dan_wisata`
  MODIFY `keypradha1` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=111;

--
-- AUTO_INCREMENT untuk tabel `tempat_wisata`
--
ALTER TABLE `tempat_wisata`
  MODIFY `keypradha2` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=97;

--
-- AUTO_INCREMENT untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `id_transaksi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=161;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `transaksi_ibfk_1` FOREIGN KEY (`keypradha1`) REFERENCES `penyedia_dan_wisata` (`keypradha1`),
  ADD CONSTRAINT `transaksi_ibfk_2` FOREIGN KEY (`keypradha2`) REFERENCES `tempat_wisata` (`keypradha2`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
